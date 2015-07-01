package com.example.blunobasicdemo;



import com.examlple.blunobasicUtil.HttpUtil;

import android.R.bool;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity  extends BlunoLibrary {
	private Button buttonScan;
	private Button buttonSerialSend;
	private EditText serialSendText;
	private TextView serialReceivedText;
	int i=0;
	int j=0;
	String error = "0";
	StringBuilder sb = new StringBuilder();
	String insertTstring="",insertPstring="",insertLstring="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
        onCreateProcess();														//onCreate Process by BlunoLibrary
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200
		
        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data
       			//initial the EditText of the sending data
        
       
        
        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});
	}

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Connected");
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
		// TODO Auto-generated method stub
		TextView stateText = new TextView(MainActivity.this);
		stateText=(TextView)findViewById(R.id.State);
		
			
		if(theString.length()>6&&i<6){
			if(theString.contains("Time")){
				sb = new StringBuilder(theString);
				i=1;
				
			}
			else if(theString.contains(":"))
			{
				
				
				sb.append(theString);
				i++;

			}
		}
		else{
			i=0;
			
			String str = sb.toString().trim();
//			boolean  f =str.endsWith("\r\n");
//			f=str.startsWith("Time");
			
			if(!str.startsWith("Time")){
//				serialReceivedText.setText("bad data!");
				String[] str1 = str.split("Tim");
				serialReceivedText.setText("");
				DealStringAndShow(str1[1]);	
			}
			else{
				serialReceivedText.setText("");
				DealStringAndShow(str);	
				}
			
		}
		
	}
	private void DealStringAndShow(String str){
		TextView stateText = new TextView(MainActivity.this);
		stateText=(TextView)findViewById(R.id.State);
		String[] strList = str.split("\r\n");
		TextView temperater = new TextView(MainActivity.this);
		TextView pulseText = new TextView(MainActivity.this);
		TextView locationText = new TextView(MainActivity.this);
		TextView speedText = new TextView(MainActivity.this);
		//TextView AllMessage = new TextView(MainActivity.this);
		temperater=(TextView)findViewById(R.id.Temperature);
		pulseText=(TextView)findViewById(R.id.pulse);
		locationText=(TextView)findViewById(R.id.location);
		speedText=(TextView)findViewById(R.id.AcceSpeed);
		
		if(strList.length>3&&strList[2].contains(":")){
			String[] temp = strList[2].split(":");
			String[] pulse = strList[3].split(":");
			String[] location = strList[1].split(":");
			String[] speed = strList[4].split(":");
			String[] abnormal=strList[5].split(":");
			
			if(!location[1].contains("per"))
			{
			String[] location1 = location[1].split("E");
			}
			if(temp.length>1){
				temperater.setText("    "+temp[1]+"摄氏度");
	//			construct the http
				insertTstring=temp[1];
				if((Double.parseDouble(insertTstring.trim())>39.0))
						{
					SmsManager sManager = SmsManager.getDefault();
					PendingIntent pi = PendingIntent.getActivity(
							MainActivity.this, 0, new Intent(), 0);
						// 发送短信
						sManager.sendTextMessage("18883869560",
						null, "请注意：老人体温过高", pi, null);
						
						}
				
			}
			if(pulse.length>1){
				pulseText.setText("    "+pulse[1]+"次/min");
	//			construct the http
				insertPstring=pulse[1];
			}
			if(location.length>1){
				locationText.setText("    "+"106.4E,29.5N");
	//			construct the http
				insertLstring="106.476753,29.571607";
			}
			if(speed.length>1){
				speedText.setText("    "+speed[1]);
			}
			if(abnormal.length>1){
				
				error = abnormal[1];
				if((Double.parseDouble(error.trim())==0))
				{
					stateText.setText("    "+"正常");
				}
				else if((Double.parseDouble(error.trim())==1))
				{
					stateText.setText("    "+"脉搏异常");
					j=0;
				}
				else if((Double.parseDouble(error.trim())==2)&&j==0)
				{
					stateText.setText("    "+"可能发生跌倒");
					SmsManager sManager = SmsManager.getDefault();
					PendingIntent pi = PendingIntent.getActivity(
							MainActivity.this, 0, new Intent(), 0);
						// 发送短信
						sManager.sendTextMessage("18883869560",
						null, "请注意：老人可能发生跌倒", pi, null);
						j=1;
				}
				else if((Double.parseDouble(error.trim())==3))
				{
					stateText.setText("    "+"体温异常");
				}
				}
				int count=0;
				for(int i=0;i<insertLstring.length();i++){
					int a = insertLstring.charAt(i);
					
					if(a==0){
						String lt=insertLstring.substring(0, i-1);
						if(count>0){
							lt+=",";
							lt+=insertLstring.substring(i+1, insertLstring.length()-1);
							insertLstring = lt;
							break;
						}
						
						count++;
					
						
					}
					
					
				}
//				
//				Runnable net = new Runnable() {
//					
//					@Override
//					public void run() {
						// TODO Auto-generated method stub
					try{
						String result =HttpUtil.InsertData(insertTstring, insertPstring, insertLstring, error);
//						System.out.println(result);
					}
					catch(Exception e){
						e.printStackTrace();
					}
//						
//					}
//				};
				
				
				
			}
		}
		
	}
	
	
	
					
