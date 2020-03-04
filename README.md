# Telegram Bot
***

## 1. Description of the project
Celem projektu było zrobienie bota w programie Telegram który pokazuje pogodę. W tym celu ja wybrałem język Java i za pomocą Telegram API (która znajduje się [tutaj](https://core.telegram.org/bots/api)) stworzyłem bota który może otrzymywać dane i wysyłać ich. Tak że użyłem bibliotekę JSoup za pomocą której pobieram (parsuję) dane z witryny internetowej która zawiera dane o pogodzie ([pogoda](https://sinoptik.pl/pogoda-rzeszów)) i wysyła ich klientowi. Wszystkie biblioteki zostały dodane do pliku pom.xml . 
***

## 2. Execution process and code
To create a bot in a telegram, we need to set up an account there and find a bot called "BotFather". Then using the following commands create a bot.

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/1.png)

Then BotFather sends us a token for access to the created bot, which will be given later in the code.
Once our bot has been created we can write the control code for it. For this we create the "BotClass" class which inherits from the "TelegramLongPollingBot" class and replace the methods.

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/2.png)

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/3.png)

As you can see, the "getBotToken" function returns the token of our bot, which we got earlier. Now, to launch the bot, you have to create the object "TelegramBotsApi" in the main method and build a project.

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/4.png)

Now the bot is running but does nothing yet. Therefore, in order to receive and analyze data, the "onUpdateReceived" method is used in the "BotClass" class.

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/5.png)

As you can see in this method, data is received and sent to the "processingRequest ()" method, which sends data to the new "Service" object that manages them.

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/6.png)

In the "Servis" class there is a "processQuery" method that checks commands, and depending on what commands the client sends, performs the appropriate action using the "Parser" object.

The "Parser" class contains methods that using the JSoup library retrieve weather data from a site and return it.

An example of the "actualTemp" method that returns a String containing the current temperature:

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/7.png)

The bot sends data using the "execute ()" function which is located in "BotClass". The argument of this function can be the object "SendMessage", therefore in the "Service" class the function "processQuery" returns the type "SendMessage" to which you can set the text (SendMessage.setText (String s);).

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/8.png)
***

## 3. Example of program operation

* "/ weekly" command

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/9.png)

* "/ daily" command

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/10.png)

* "/ sunset" and "/ temp" commands

![](https://github.com/NikitaVovk/TelegramBot/blob/master/ScreenShots/11.png)
