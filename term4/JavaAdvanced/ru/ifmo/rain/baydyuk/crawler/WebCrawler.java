package ru.ifmo.ctddev.rodionova.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class recursively walks sites.
 *
 * @author Anna Rodionova
 */
public class WebCrawler implements Crawler {
    private final Downloader downloader;
    private final ExecutorService downloadPool;
    private final ExecutorService extractPool;
    private final int perHost;

    /**
     * Creates an instance of {@code WebCrawler} with parameters.
     *
     * @param downloader  downloader allows you to download page and extract links
     * @param downloaders the maximum number of simultaneously loaded pages
     * @param extractors  the maximum number of pages which are extracted from the reference
     * @param perHost     the maximum number of pages loaded at the same time from one host
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.perHost = perHost;
        this.downloadPool = Executors.newFixedThreadPool(downloaders);
        this.extractPool = Executors.newFixedThreadPool(extractors);
    }

    /**
     * Recursively walks the pages starting from the specified URL to the predetermined depth
     * and returns a list of downloaded pages and files and a list of errors.
     *
     * @param url   starting URL
     * @param depth depth of recursive walking
     * @return list of downloaded pages and files and list of errors
     */
    @Override
    public Result download(String url, int depth) {
        final Map<String, IOException> errors = new ConcurrentHashMap<>();
        final Queue<String> queue = new ConcurrentLinkedQueue<>();
        final Set<String> urls = new ConcurrentSkipListSet<>();
        queue.add(url);
        urls.add(url);
        for (int i = 0; i < depth; ++i) {
            final int currentDepth = i;
            final Set<String> set = new ConcurrentSkipListSet<>();
            final Map<String, ConcurrentLinkedQueue<String>> perHostMap = new ConcurrentHashMap<>();
            final Map<String, Integer> semaphores = new ConcurrentHashMap<>();
            final ConcurrentLinkedQueue<Future> pending = new ConcurrentLinkedQueue<>();
            for (String u : queue) {
                String host;
                try {
                    host = URLUtils.getHost(u);
                } catch (MalformedURLException e) {
                    continue;
                }
                perHostMap.computeIfAbsent(host, k -> new ConcurrentLinkedQueue<>()).add(u);
                synchronized (semaphores) {
                    Integer c = semaphores.putIfAbsent(host, 0);
                    if (c == null) {
                        c = 0;
                    }
                    if (c < perHost) {
                        semaphores.put(host, c + 1);
                        pending.add(downloadPool.submit(() -> {
                            while (true) {
                                String cUrl = "";
                                synchronized (perHostMap) {
                                    if (!perHostMap.get(host).isEmpty()) {
                                        cUrl = perHostMap.get(host).poll();
                                    }
                                }
                                if (cUrl.length() > 0) {
                                    final String copy = cUrl;
                                    try {
                                        Document document = downloader.download(cUrl);
                                        if (currentDepth + 1 < depth) {
                                            pending.add(extractPool.submit(() -> {
                                                try {
                                                    List<String> list = document.extractLinks();
                                                    for (String link : list) {
                                                        synchronized (urls) {
                                                            if (!urls.contains(link)) {
                                                                urls.add(link);
                                                                set.add(link);
                                                            }
                                                        }
                                                    }
                                                } catch (IOException e) {
                                                    errors.putIfAbsent(copy, e);
                                                }
                                            }));
                                        }
                                    } catch (IOException e) {
                                        errors.putIfAbsent(cUrl, e);
                                    }
                                } else {
                                    synchronized (semaphores) {
                                        semaphores.put(host, semaphores.get(host) - 1);
                                    }
                                    return;
                                }
                            }
                        }));
                    }
                }
            }
            while (!pending.isEmpty()) {
                Future future = pending.poll();
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                }
            }
            queue.clear();
            queue.addAll(set);
        }
        urls.removeAll(errors.keySet());
        return new Result(new ArrayList<>(urls), errors);
    }

    /**
     * Completes all helper threads.
     */
    @Override
    public void close() {
        downloadPool.shutdown();
        extractPool.shutdown();
    }

    /**
     * Run class.
     *
     * @param args parameters for {@code WebCrawler}
     * @throws IOException if invalid arguments
     */
    public static void main(String[] args) throws IOException {
        String url = "";
        if (args.length == 0) {
            url = "http://www.google.ru";
        } else {
            url = args[0];
        }
        int downloaders = 5;
        int extractors = 5;
        int perHost = 5;
        if (args.length > 1) {
            downloaders = Integer.parseInt(args[1]);
        }
        if (args.length > 2) {
            extractors = Integer.parseInt(args[2]);
        }
        if (args.length > 3) {
            perHost = Integer.parseInt(args[3]);
        }
        try (Crawler crawler = new WebCrawler(new CachingDownloader(Paths.get("./CachedSites")), downloaders, extractors, perHost)) {
            Result links = crawler.download(url, 2);
            System.out.println(links.getDownloaded().size());
        }
    }
}

