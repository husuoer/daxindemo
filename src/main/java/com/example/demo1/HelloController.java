package com.example.demo1;



import com.alibaba.fastjson2.JSONObject;
import com.example.demo1.bean.trade.param.PlaceOrder;
import com.example.demo1.config.APIConfiguration;
import com.example.demo1.exception.APIException;
import com.example.demo1.service.trade.TradeAPIService;
import com.example.demo1.service.trade.impl.TradeAPIServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class HelloController {
    @FXML
    private TextField apiKey;
    @FXML
    private TextField secret;
    @FXML
    private TextArea addressStatus;
    @FXML
    private Button startMine;
    @FXML
    private Button stopMine;
    @FXML
    private TextArea mineLog;
    @FXML
    private Button addAccount;
    @FXML
    private Button cleanAccount;
    @FXML
    private ChoiceBox<FXCollections> enation;

    @FXML
    private TextField pair;
    @FXML
    private TextField price;
    @FXML
    private TextField num;
    @FXML
    private TextField startTime;
    @FXML
    private TextField passPhase;

    private boolean flag=false;
    private boolean startflag=true;

    private Map<String,Map<String,String>> okexApiMap = new ConcurrentHashMap<>();

    @FXML
    public void startMineEvent(ActionEvent event){
        if(startflag){
            startflag =false;
            flag=true;
            daxin();
        }
    }
    @FXML
    public void stopMineEvent(ActionEvent event){
        if(!startflag){
            startflag = true;
            flag = false;
        }
    }
    @FXML
    public void addAccount(ActionEvent event){
//        System.out.println("添加账户"+address.getText());
        if(enation.getSelectionModel().getSelectedIndex()==0){
            Map<String,String> map = new HashMap<>();
            map.put("apiKey",apiKey.getText());
            map.put("secret",secret.getText());
            map.put("passPhase",passPhase.getText());
            okexApiMap.put(apiKey.getText(),map);
            for (Map.Entry<String, Map<String,String>> entry :
                    okexApiMap.entrySet()) {
                addressStatus.appendText("当前Okex有效打新账户:" + "\n");
                addressStatus.appendText(entry.getKey() + "\n");
            }
        }
    }
    @FXML
    public void cleanAccount(ActionEvent event){
        if(enation.getSelectionModel().getSelectedIndex()==0) {
            okexApiMap.clear();
            for (Map.Entry<String, Map<String,String>> entry :
                    okexApiMap.entrySet()) {
                addressStatus.appendText("当前有效Okex打新账户已经全部删除。" + "\n");
            }
        }

    }
    @FXML
    public void deleteAccount(ActionEvent event) {
        if(enation.getSelectionModel().getSelectedIndex()==0) {
            okexApiMap.remove(apiKey.getText());
            for (Map.Entry<String, Map<String,String>> entry :
                    okexApiMap.entrySet()) {
                addressStatus.appendText("当前Okex有效打新账户:" + "\n");
                addressStatus.appendText(entry.getKey() + "\n");
            }
        }
    }
    @FXML
    public void selectEnation(ActionEvent actionEvent) {
        System.out.println(enation.getSelectionModel().getSelectedIndex());

    }

    private void daxin(){
        APIConfiguration config = new APIConfiguration("https://www.okx.com");
        config.setApiKey(apiKey.getText());
        config.setSecretKey(secret.getText());
        config.setPassphrase(passPhase.getText());
        TradeAPIService tradeAPIService = new TradeAPIServiceImpl(config);
        PlaceOrder placeOrder = new PlaceOrder();
        placeOrder.setInstId(pair.getText().toUpperCase(Locale.ROOT));
        String orderId = UUID.randomUUID().toString().replace("-","").toLowerCase(Locale.ROOT).substring(10);
        placeOrder.setTdMode("cash");
        placeOrder.setClOrdId(orderId);
        placeOrder.setSide("buy");
        placeOrder.setOrdType("limit");
        placeOrder.setSz(num.getText());//数量
        placeOrder.setPx(price.getText());//价格
//        placeOrder.setTag("0423a3a06");
//        String placeOrderString = "{" +
//                "instId='" + pair.getText().toLowerCase(Locale.ROOT) + '\'' +
//                ", tdMode='" + "cash" + '\'' +
//                ", ccy='" + "" + '\'' +
//                ", clOrdId='" + orderId + '\'' +
//                ", tag='" + "" + '\'' +
//                ", side='" + "buy" + '\'' +
//                ", posSide='" + "" + '\'' +
//                ", ordType='" + "limit" + '\'' +
//                ", sz='" + num.getText() + '\'' +
//                ", px='" + price.getText() + '\'' +
//                ", reduceOnly='" + "" + '\'' +
//                '}';

        //构建好参数
        mineLog.appendText("参数拼接完成"+"\n");
        mineLog.appendText("等待抢购开始"+"\n");
        Thread thread = new Thread() {
            public void run() {
                while (flag){
                    //一顿乱买
                    if(System.currentTimeMillis()>=Long.parseLong(startTime.getText())){
                        try{
                            JSONObject result = tradeAPIService.placeOrder(placeOrder);
                            mineLog.appendText("调用接口成功"+"\n");
                            mineLog.appendText("接口返回data中sCode为0即为下单成功"+"\n");
                            mineLog.appendText(result.toJSONString());
                            flag = false;
                        }catch (APIException e){
                            mineLog.appendText("调用接口异常"+"\n");
                            mineLog.appendText(e.getMessage());

                        }
                    }
                }
            }
        };
        thread.start();

    }

}