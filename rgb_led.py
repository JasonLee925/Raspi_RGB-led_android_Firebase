#coding=utf-8
from gpiozero import LED
from requests import get
import json
import time

#firebase資料庫 url
Firebase_URL = 'https://raspi-led-example.firebaseio.com/.json'

led1_17 = LED(17) #LED1_Green
led1_22 = LED(22) #LED1_Blue
led1_27 = LED(27) #LED1_Red

led2_14 = LED(14) #LED2_Green
led2_15 = LED(15) #LED2_Blue
led2_18 = LED(18) #LED2_Red

#初始化LED燈
led1_17.off()
led1_22.off()
led1_27.off()
led2_14.off()
led2_15.off()
led2_18.off()

while True:
	#讀取firebase上資料庫的資料
    Led1_Data = get(Firebase_URL).json()['led']
    Led2_Data = get(Firebase_URL).json()['led2']
    flash = get(Firebase_URL).json()['flash'];
    
	#燈號顯示
    led1_17.value = Led1_Data['Green']
    led1_22.value = Led1_Data['Blue']
    led1_27.value = Led1_Data['Red']
    led2_14.value = Led2_Data['Green']
    led2_15.value = Led2_Data['Blue']
    led2_18.value = Led2_Data['Red']
    
	#閃爍功能
    if flash == 1:
        count=0
        for count in range(0,8):
            if count%2==0:
                led1_17.value=0
                led2_14.value=0
            else:
                led1_17.value=1
                led2_14.value=1
            if count<4:
                led1_22.value=0
                led2_15.value=0
            else:
                led1_22.value=1
                led2_15.value=1
            if count==0 or count==1 or count==4 or count==5:
                led1_27.value=0
                led2_18.value=0
            else:
                led1_27.value=1
                led2_18.value=1
            time.sleep(.300)
            if flash == 0:
                led1_17.value=0
                led2_14.value=0
                led1_22.value=0
                led2_15.value=0
                led1_27.value=0
                led2_18.value=0

