import org.jsoup.Jsoup;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Document;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Service  {

    private String url="//sinoptik.pl/pogoda-rzeszów";



    private org.jsoup.nodes.Document getPage(String url) throws IOException {
        // String url ="https://sinoptik.pl/pogoda-rzeszów";
        org.jsoup.nodes.Document page =  Jsoup.parse(new URL("https:"+url),3000);
        return page;
    }

    public synchronized SendMessage processQuery(Message msg) throws IOException {
        System.out.println(msg.getText());

        SendMessage sndMsg=new SendMessage();
        sndMsg.setChatId(msg.getChatId());
        Parser parser=new Parser();

        url ="//sinoptik.pl/pogoda-rzeszów";


        if (msg.getText().equals("/help")){

                sndMsg.setText("Hi I am a weathercoast bot");
        }
        else  if (msg.getText().equals("/weekly")){
            String weekly="";

            for (int i=0;i<7;i++){
                weekly=weekly+"✅"+parser.weeklyWeather(getPage(url)).get(i);
            }
            sndMsg.setText(weekly);


        }
        else if(msg.getText().equals("/daily")){

            sndMsg=sendInlineKeyBoardMessage(msg.getChatId(),parser,msg.getText());

        }
        else  if (msg.getText().equals("/temp")){

            sndMsg.setText( parser.actualTemp(getPage(url)));
            }


        else  if (msg.getText().equals("/sunset")){

            sndMsg.setText( parser.sunsetInfo(getPage(url)));

        }
        else
            sndMsg.setText("Command not found");

    return sndMsg;
    }


    public  SendMessage processCallBackQuery(Update update) throws IOException {
        SendMessage sndMsg = new SendMessage();
        sndMsg.setChatId(update.getCallbackQuery().getMessage().getChatId());
        String infoDaily="";
        Parser prs = new Parser();
        String newUrl;
        if (update.getCallbackQuery().getData().startsWith("wth")){
           // System.out.println(Character.getNumericValue(update.getCallbackQuery().getData().charAt(3)));

        newUrl=prs.getOtherDaysUrl(getPage(url),url).get(Character.getNumericValue(update.getCallbackQuery().getData().charAt(3)));
            System.out.println(newUrl);
            for(String info :new Parser().dailyWeather(getPage(newUrl)))
                infoDaily=infoDaily+"✅"+info;
        }

        return sndMsg.setText(infoDaily);
    }


    public synchronized SendMessage sendInlineKeyBoardMessage(long chatId,Parser parser,String request) {       //BLOK BUTTONS Z LABELS DATES

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();



        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        ArrayList<String> dates = null;
        try {
            dates = parser.dates(getPage(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText(dates.get(0)).setCallbackData("wth0"));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText(dates.get(1)).setCallbackData("wth1"));
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(new InlineKeyboardButton().setText(dates.get(2)).setCallbackData("wth2"));
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        keyboardButtonsRow4.add(new InlineKeyboardButton().setText(dates.get(3)).setCallbackData("wth3"));
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        keyboardButtonsRow5.add(new InlineKeyboardButton().setText(dates.get(4)).setCallbackData("wth4"));
        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        keyboardButtonsRow6.add(new InlineKeyboardButton().setText(dates.get(5)).setCallbackData("wth5"));
        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        keyboardButtonsRow7.add(new InlineKeyboardButton().setText(dates.get(6)).setCallbackData("wth6"));


        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Wybierz dzień").setReplyMarkup(inlineKeyboardMarkup);


    }
}
