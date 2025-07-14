package com.elm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 微服务一键启动器
 * 用于启动饿了么微服务系统中的所有服务
 */
public class ServiceStarter {

    // 基础服务列表及其启动顺序
    private static final List<ServiceInfo> BASIC_SERVICES = Arrays.asList(
            new ServiceInfo("elm-registry", "注册中心", 8761, 15),
            new ServiceInfo("elm-config", "配置中心", 8888, 10),
            new ServiceInfo("elm-gateway", "API网关", 8082, 5)
    );

    // 业务服务列表
    private static final List<ServiceInfo> BUSINESS_SERVICES = Arrays.asList(
            new ServiceInfo("elm-user", "用户服务", 8083, 0),
            new ServiceInfo("elm-business", "商家服务", 8084, 0),
            new ServiceInfo("elm-order", "订单服务", 8085, 0),
            new ServiceInfo("elm-delivery", "配送服务", 8086, 0),
            new ServiceInfo("elm-payment", "支付服务", 8087, 0),
            new ServiceInfo("elm-rating", "评价服务", 8088, 0)
    );

    // 所有服务列表
    private static final List<ServiceInfo> ALL_SERVICES = new ArrayList<ServiceInfo>() {{
        addAll(BASIC_SERVICES);
        addAll(BUSINESS_SERVICES);
    }};

    // 线程池，用于并行启动服务
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    // 存储已启动的进程
    private static final Map<String, Process> runningProcesses = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("请输入选项: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startAllServices();
                    break;
                case "2":
                    startBasicServices();
                    break;
                case "3":
                    startBusinessServices();
                    break;
                case "4":
                    stopAllServices();
                    break;
                case "5":
                    checkServiceStatus();
                    break;
                case "0":
                    running = false;
                    stopAllServices();
                    executor.shutdown();
                    System.out.println("程序已退出，所有服务已停止。");
                    break;
                default:
                    System.out.println("无效选项，请重新输入。");
            }
        }
    }

    /**
     * 打印主菜单
     */
    private static void printMenu() {
        System.out.println("\n===== 饿了么微服务启动器 =====");
        System.out.println("1. 启动所有服务");
        System.out.println("2. 只启动基础服务(注册中心、配置中心、网关)");
        System.out.println("3. 只启动业务服务(用户、商家、订单等)");
        System.out.println("4. 停止所有服务");
        System.out.println("5. 检查服务状态");
        System.out.println("0. 退出程序");
        System.out.println("==========================");
    }

    /**
     * 启动所有服务
     */
    private static void startAllServices() {
        System.out.println("正在启动所有微服务...");
        
        // 先启动基础服务
        startBasicServices();
        
        System.out.println("等待基础服务就绪...");
        try {
            Thread.sleep(20000); // 等待20秒，确保基础服务已就绪
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 再启动业务服务
        startBusinessServices();
        
        System.out.println("所有服务启动完成！");
        printServiceUrls();
    }

    /**
     * 启动基础服务
     */
    private static void startBasicServices() {
        System.out.println("正在启动基础服务...");
        startServices(BASIC_SERVICES);
    }

    /**
     * 启动业务服务
     */
    private static void startBusinessServices() {
        System.out.println("正在启动业务服务...");
        startServices(BUSINESS_SERVICES);
    }

    /**
     * 启动指定的服务列表
     */
    private static void startServices(List<ServiceInfo> services) {
        CountDownLatch latch = new CountDownLatch(services.size());
        
        for (ServiceInfo service : services) {
            executor.submit(() -> {
                try {
                    if (!isServiceRunning(service.getName())) {
                        System.out.println("启动 " + service.getDescription() + " (" + service.getName() + ")...");
                        startService(service);
                        
                        // 等待指定的时间
                        if (service.getWaitTime() > 0) {
                            System.out.println("等待 " + service.getName() + " 就绪... (" + service.getWaitTime() + "秒)");
                            Thread.sleep(service.getWaitTime() * 1000L);
                        }
                    } else {
                        System.out.println(service.getDescription() + " (" + service.getName() + ") 已经在运行中。");
                    }
                } catch (Exception e) {
                    System.err.println("启动 " + service.getName() + " 失败: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            // 等待所有服务启动完成
            latch.await(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("启动服务被中断: " + e.getMessage());
        }
    }

    /**
     * 启动单个服务
     */
    private static void startService(ServiceInfo service) throws IOException {
        String servicePath = new File(service.getName()).getAbsolutePath();
        
        // 检查目录是否存在
        if (!Files.exists(Paths.get(servicePath))) {
            throw new IOException("服务目录不存在: " + servicePath);
        }
        
        // 构建启动命令
        ProcessBuilder builder = new ProcessBuilder();
        
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // Windows系统
            builder.command("cmd", "/c", "mvn spring-boot:run");
        } else {
            // Linux/Mac系统
            builder.command("sh", "-c", "mvn spring-boot:run");
        }
        
        builder.directory(new File(servicePath));
        
        // 重定向输出到文件
        Path logDir = Paths.get("logs");
        if (!Files.exists(logDir)) {
            Files.createDirectories(logDir);
        }
        
        File logFile = new File("logs/" + service.getName() + ".log");
        builder.redirectOutput(logFile);
        builder.redirectError(logFile);
        
        // 启动进程
        Process process = builder.start();
        runningProcesses.put(service.getName(), process);
    }

    /**
     * 停止所有服务
     */
    private static void stopAllServices() {
        System.out.println("正在停止所有服务...");
        
        // 先停止业务服务，再停止基础服务（与启动顺序相反）
        List<ServiceInfo> reversedServices = new ArrayList<>(ALL_SERVICES);
        Collections.reverse(reversedServices);
        
        for (ServiceInfo service : reversedServices) {
            stopService(service.getName());
        }
        
        System.out.println("所有服务已停止。");
    }

    /**
     * 停止指定服务
     */
    private static void stopService(String serviceName) {
        Process process = runningProcesses.get(serviceName);
        if (process != null && process.isAlive()) {
            System.out.println("停止服务: " + serviceName);
            process.destroy();
            
            try {
                // 等待进程终止
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    // 如果进程在10秒内没有终止，强制终止
                    process.destroyForcibly();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            runningProcesses.remove(serviceName);
        }
    }

    /**
     * 检查服务是否正在运行
     */
    private static boolean isServiceRunning(String serviceName) {
        Process process = runningProcesses.get(serviceName);
        return process != null && process.isAlive();
    }

    /**
     * 检查所有服务状态
     */
    private static void checkServiceStatus() {
        System.out.println("\n===== 服务状态 =====");
        
        for (ServiceInfo service : ALL_SERVICES) {
            String status = isServiceRunning(service.getName()) ? "运行中" : "已停止";
            System.out.printf("%-15s %-10s %-10s http://localhost:%d%n", 
                    service.getName(), service.getDescription(), status, service.getPort());
        }
        
        System.out.println("===================");
    }

    /**
     * 打印服务访问地址
     */
    private static void printServiceUrls() {
        System.out.println("\n===== 服务访问地址 =====");
        System.out.println("注册中心: http://localhost:8761");
        System.out.println("配置中心: http://localhost:8888");
        System.out.println("API网关: http://localhost:8082");
        System.out.println("用户服务: http://localhost:8083");
        System.out.println("商家服务: http://localhost:8084");
        System.out.println("订单服务: http://localhost:8085");
        System.out.println("配送服务: http://localhost:8086");
        System.out.println("支付服务: http://localhost:8087");
        System.out.println("评价服务: http://localhost:8088");
        System.out.println("所有API请求应通过API网关(http://localhost:8082)访问");
        System.out.println("========================");
    }

    /**
     * 服务信息类
     */
    private static class ServiceInfo {
        private final String name;
        private final String description;
        private final int port;
        private final int waitTime; // 等待时间（秒）

        public ServiceInfo(String name, String description, int port, int waitTime) {
            this.name = name;
            this.description = description;
            this.port = port;
            this.waitTime = waitTime;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getPort() {
            return port;
        }

        public int getWaitTime() {
            return waitTime;
        }
    }
} 