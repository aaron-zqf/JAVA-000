学习笔记

client

    public class Client {
        public static void main(String[] args) throws IOException {
            for (int i = 0; i < 100; i++) {
                callService();
            }
        }
    
        private static void callService() throws IOException {
            HttpClientBuilder httpClient = HttpClientBuilder.create();
            HttpUriRequest request = new HttpGet("http://localhost:9999");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD).build();
            httpClient.setDefaultRequestConfig(requestConfig);
            System.out.println("开始调用服务");
            CloseableHttpResponse execute = httpClient.build().execute(request);
            System.out.println("服务调用结束");
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));
    
            String str = null;
            while((str = br.readLine()) != null){
                System.out.println(str);
                if (str.length() == 0){
                    break;
                }
            }
            br.close();
        }
    }

server

    public class Service {
        public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(9999);
            System.out.println("服务端启动成功...");
            
            while (true) {
                final Socket socket = ss.accept();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            InputStream inputStream1 = socket.getInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream1));
                            String str = null;
                            while ((str = br.readLine()) != null) {
                                System.out.println(str);
                                if (str.length() == 0){
                                    break;
                                }
                            }
                            
                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStream);
                            printWriter.println("HTTP/1.1 200 OK");
                            printWriter.println("Content-Type: text/html; charset=utf-8");
                            printWriter.println("");
                            printWriter.write("<h1>响应报文</h1>");
                            printWriter.flush();
                            printWriter.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
