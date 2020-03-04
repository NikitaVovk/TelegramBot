import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;


public class BotClass extends TelegramLongPollingBot {
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public synchronized void onUpdateReceived(Update update) {
        Message  message = update.getMessage();


         if(update.hasCallbackQuery()){
             System.out.println(update.getCallbackQuery().getData());
             try {
                 execute(new Service().processCallBackQuery(update));
                 simpleCallbackAnswer(update.getCallbackQuery().getId());
             } catch (TelegramApiException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

        }

        if (message!=null&&message.hasText()){

            try {
                processingRequest(message,update);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }

    }






    public synchronized void processingRequest(Message msg,Update update) throws IOException, TelegramApiException {


            execute(new Service().processQuery(msg));


    }
    private void simpleCallbackAnswer(String queryId) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(queryId);
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }





    public synchronized void sendMsg(String chatId, String s) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        sendMessage(sendMessage);

    }



    @Override
    public String getBotUsername() {
        return "Weather PL";
    }


    @Override
    public String getBotToken() {
        return "650459248:AAHhIIX1mZZVJpXoRpputl74_03FhdacWLg";
    }
}

