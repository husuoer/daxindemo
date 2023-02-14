//package com.example.demo1;
//
//import com.google.gson.Gson;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import okhttp3.*;
//import org.web3j.abi.FunctionEncoder;
//import org.web3j.abi.datatypes.Function;
//import org.web3j.abi.datatypes.Type;
//import org.web3j.abi.datatypes.Uint;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.methods.response.EthSendTransaction;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.tx.RawTransactionManager;
//import org.web3j.tx.TransactionManager;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//
//
//public class HelloController {
//    @FXML
//    private TextField address;
//    @FXML
//    private TextField pk;
//    @FXML
//    private TextArea addressStatus;
//    @FXML
//    private Button startMine;
//    @FXML
//    private Button stopMine;
//    @FXML
//    private TextArea mineLog;
//    @FXML
//    private Button addAccount;
//    @FXML
//    private Button cleanAccount;
//
//    MintTask mintTask = new MintTask("mintTask");
//    private Timer timer;
//    @FXML
//    public void startMineEvent(ActionEvent event){
//        mintTask.status = true;
//        timer = new Timer();
//        timer.schedule(mintTask,10000L,90000L);
//        mineLog.appendText("开始挖矿"+"\n");
//    }
//    @FXML
//    public void stopMineEvent(ActionEvent event){
//        mintTask.status = false;
//        timer.cancel();
//        mineLog.appendText("结束挖矿"+"\n");
//    }
//    @FXML
//    public void addAccount(ActionEvent event){
////        System.out.println("添加账户"+address.getText());
//        mintTask.pkAndAddressMap.put(address.getText(),pk.getText());
//        addressStatus.appendText("添加地址:"+address.getText()+"\n");
//        for (Map.Entry<String,String> entry:
//        mintTask.pkAndAddressMap.entrySet()) {
//            addressStatus.appendText("当前有效挖矿账户:"+"\n");
//            addressStatus.appendText(entry.getKey()+"\n");
//        }
//    }
//    @FXML
//    public void cleanAccount(ActionEvent event){
//        mintTask.pkAndAddressMap.clear();
//        addressStatus.appendText("清除全部账户"+"\n");
//    }
//    @FXML
//    public void deleteAccount(ActionEvent event) {
//        mintTask.pkAndAddressMap.remove(address.getText());
//        addressStatus.appendText("删除地址:"+address.getText()+"\n");
//        for (Map.Entry<String,String> entry:
//                mintTask.pkAndAddressMap.entrySet()) {
//            addressStatus.appendText("当前有效挖矿账户:"+"\n");
//            addressStatus.appendText(entry.getKey()+"\n");
//        }
//    }
//
//    public void selectEnation(ActionEvent actionEvent) {
//    }
//
//    class MintTask extends TimerTask {
//
//
//        String contractAddress = "0x9ab9e81Be39b73de3CCd9408862b1Fc6D2144d2B";
//
//
//        Web3j web3 = Web3j.build(new HttpService("https://subnets.avax.network/swimmer/mainnet/rpc"));
//
//        public String startGame(String pk, BigInteger teamId) throws ExecutionException, InterruptedException, IOException {
//
//
//            Credentials credentials = Credentials.create(pk);
//            Function function = new Function(
//                    "startGame",  // function we're calling
//                    Arrays.<Type>asList(new Uint(teamId)),  // Parameters to pass as Solidity Types
//                    Collections.emptyList());
//            String encodedFunction = FunctionEncoder.encode(function);
//
//            long chainId = 73772;
//            TransactionManager transactionManager = new RawTransactionManager(web3,credentials,chainId);
//            EthSendTransaction ethSendTransaction=transactionManager.sendEIP1559Transaction(chainId,new BigInteger("1000000000"),new BigInteger("20001000000000"),new BigInteger("400000"),contractAddress,encodedFunction,BigInteger.ZERO);
//            String transactionHash = ethSendTransaction.getTransactionHash();
//            return transactionHash;
//        }
//        public String closeGame(String pk,BigInteger gameId) throws ExecutionException, InterruptedException, IOException {
//
//            Credentials credentials = Credentials.create(pk);
//            Function function = new Function(
//                    "closeGame",  // function we're calling
//                    Arrays.<Type>asList(new Uint(gameId)),  // Parameters to pass as Solidity Types
//                    Collections.emptyList());
//            String encodedFunction = FunctionEncoder.encode(function);
//            long chainId = 73772;
//            TransactionManager transactionManager = new RawTransactionManager(web3,credentials,chainId);
//            EthSendTransaction ethSendTransaction=transactionManager.sendEIP1559Transaction(chainId,new BigInteger("1000000000"),new BigInteger("20001000000000"),new BigInteger("400000"),contractAddress,encodedFunction,BigInteger.ZERO);
//            String transactionHash = ethSendTransaction.getTransactionHash();
//            return transactionHash;
//        }
//
//
//        String uri = "https://idle-game-api.crabada.com/public/idle/mines?page=1&status=open&limit=8&user_address=";
//        String startUri = "https://idle-game-api.crabada.com/public/idle/teams?limit=5&page=1&is_team_available=1&user_address=";
//
//
//        String gasUrl = "https://gavax.blockscan.com/gasapi.ashx?apikey=key&method=gasoracle";
//        public ConcurrentHashMap<String,String> waitCloseMap = new ConcurrentHashMap();
//        public ConcurrentHashMap<String,String> waitStartMap = new ConcurrentHashMap();
//        public ConcurrentHashMap<String,String> pkAndAddressMap = new ConcurrentHashMap<>();
//
//
//
//        private boolean checkGas() {
//            try {
//
//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                    .proxy(new Proxy(Proxy.Type.HTTP, sa))
//                        .connectTimeout(15, TimeUnit.SECONDS)
//                        .writeTimeout(15, TimeUnit.SECONDS)
//                        .readTimeout(15, TimeUnit.SECONDS)
//                        .build();
//                //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式
//                Request request = new Request.Builder().url(gasUrl).method("GET", null).build();
//                Call call = okHttpClient.newCall(request);
//                Response response = call.execute();
//                ResponseBody responseBody = response.body();
//                String rString = responseBody.string();
//                Gson gson = new Gson();
//                Map map = gson.fromJson(rString, Map.class);
//                Map<String, Object> resultMap = (Map) map.get("result");
//                String fastGas = (String) resultMap.get("FastGasPrice");
//                Double gas = Double.valueOf(fastGas);
//                Double biaozhun = 190d;
//                if (gas.compareTo(biaozhun) >= 0) {
//                    return false;
//                }
//                mineLog.appendText("当前gas:"+gas+"\n");
//                return true;
//            }catch (Exception e){
//                mineLog.appendText("获取gas出错"+"\n");
//                return false;
//            }
//        }
//
//        private void startGame() {
//            for (Map.Entry entry:
//                    waitStartMap.entrySet()) {
//                String gameId = entry.getKey().toString();
//                String pk = entry.getValue().toString();
//                mineLog.appendText("挖矿开始，teamId："+gameId+"\n");
//                try {
//                    this.startGame(pk,new BigInteger(gameId));
//
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            waitStartMap.clear();
//        }
//
//        private void initStartMap(String address, String pk) {
//            String url = startUri+address;
//            try{
//
//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                    .proxy(new Proxy(Proxy.Type.HTTP, sa))
//                        .connectTimeout(15, TimeUnit.SECONDS)
//                        .writeTimeout(15, TimeUnit.SECONDS)
//                        .readTimeout(15, TimeUnit.SECONDS)
//                        .build();
//                //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//                Request request = new Request.Builder().url(url).method("GET",null).build();
//                Call call = okHttpClient.newCall(request);
//                Response response = call.execute();
//                ResponseBody responseBody = response.body();
//                String rString = responseBody.string();
//                Gson gson = new Gson();
//                Map map = gson.fromJson(rString,Map.class);
//                Map resultMap = (Map) map.get("result");
//                if(resultMap.get("data")!=null){
//                    List<Map<String,Object>> resultList = (List) resultMap.get("data");
//                    for (Map<String,Object> team: resultList) {
//                        Double dTeamId = (Double) team.get("team_id");
//                        Integer teamId = dTeamId.intValue();
//                        waitStartMap.put(teamId+"",pk);
//                    }
//                }
//            }catch (Exception e){
//                mineLog.appendText("获取需要开始的队伍超时"+"\n");
//            }
//        }
//
//        private void closeGame() {
//            for (Map.Entry entry:
//                    waitCloseMap.entrySet()) {
//                String gameId = entry.getKey().toString();
//                String pk = entry.getValue().toString();
//                mineLog.appendText("挖矿结束，gameId："+gameId+"\n");
//                try {
//                    this.closeGame(pk,new BigInteger(gameId));
//
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            waitCloseMap.clear();
//
//        }
//
//        private void initCloseMap(String address,String pk) {
//            String url = uri+address;
//            try{
//
//
//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                        .connectTimeout(15, TimeUnit.SECONDS)
//                        .writeTimeout(15, TimeUnit.SECONDS)
//                        .readTimeout(15, TimeUnit.SECONDS)
//                        .build();
//
//                //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
//                Request request = new Request.Builder().url(url).method("GET",null).build();
//                Call call = okHttpClient.newCall(request);
//                Response response = call.execute();
//                ResponseBody responseBody = response.body();
//                String rString = responseBody.string();
//                Gson gson = new Gson();
//                Map map = gson.fromJson(rString,Map.class);
//                Map resultMap = (Map) map.get("result");
//                List<Map<String,Object>> resultList = (List) resultMap.get("data");
//                for (Map<String,Object> team: resultList) {
//                    Double dGameId = (Double) team.get("game_id");
//                    Integer gameId = dGameId.intValue();
//
//                    List<Map<String,Object>> timeList = (List<Map<String,Object>>) team.get("process");
//                    for (Map<String,Object> timeMap: timeList) {
//                        String action = (String) timeMap.get("action");
//                        if("create-game".equals(action)){
//                            Integer cgTime = ((Double) timeMap.get("transaction_time")).intValue();
//                            Long currentTimeLong = System.currentTimeMillis()/1000;
//                            Integer currentTime = currentTimeLong.intValue();
//                            if(currentTime>cgTime+14400){
////                                log.info(gameId.toString());
//                                waitCloseMap.put(gameId+"",pk);
//                            }
//                        }
//                    }
//                }
//            }catch (Exception e){
//                mineLog.appendText("获取需要关闭的队伍超时"+"\n");
//
//            }
//
//        }
//
//        @Override
//        public void run() {
//            if(status) {
//
//                    mineLog.appendText("执行挖矿任务：" + LocalDateTime.now().toString()+"\n");
//                    for (Map.Entry<String, String> entry :
//                            pkAndAddressMap.entrySet()) {
//                        initCloseMap(entry.getKey(), entry.getValue());
//                        closeGame();
//                    }
//                    for (Map.Entry<String, String> entry :
//                            pkAndAddressMap.entrySet()) {
//                        initStartMap(entry.getKey(), entry.getValue());
//                        startGame();
//                    }
//                    mineLog.appendText("执行挖矿任务完毕：" + LocalDateTime.now().toString()+"\n");
//
//            }
//        }
//        private String taskName;
//
//        public MintTask(String taskName) {
//            this.taskName = taskName;
//        }
//        public boolean status = false;
//    }
//
//}