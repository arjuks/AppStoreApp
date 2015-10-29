package com.example.arjun.midtermexample;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by arjun on 10/25/2015.
 */


//
//


    public class AppsUtil {
    static String id;
    static String title;
    static String appurl;
    static String devname;
    static String date;
    static String price;
    static String simg;
    static String img;
    static Boolean imageh53 = false;
    static Boolean imageh100 = false;


        static ArrayList<Apps> parsePodcast(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Apps podcast = null;
            ArrayList<Apps> podcastlist = new ArrayList<Apps>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch(event) {
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("entry")) {
                            podcast = new Apps();
                            //podcast.setId(parser.getAttributeValue(null,"id"));
                        }
                        else if(parser.getName().equals("id")) {
                            //id = podcast.setId(parser.getAttributeValue(null,"im:id"));
                            id = parser.getAttributeValue(null, "im:id");
                            Log.d("demo","id"+ id);
                        }
                        else if(parser.getName().equals("link")) {
                           // appurl = podcast.setAppurl(parser.getAttributeValue(null, "href"));
                            appurl = parser.getAttributeValue(null, "href");
                            Log.d("demo", "link" + appurl);
                            //appurl = attributes.getValue("href");

                        }
                        else if(parser.getName().equals("im:price")) {
                            //app.setAppurl(attributes.getValue("href"));
                            price = parser.getAttributeValue(null, "amount");
                            Log.d("demo", "price" + price);

                        }

                        else if(parser.getName().equals("title")) {
                            title = parser.nextText().trim();
                            Log.d("demo", "title" + title);

                        }
                        else if(parser.getName().equals("im:releaseDate")) {
                            date = parser.nextText().trim();
                            Log.d("demo", "date" + date);

                        }
                        else if(parser.getName().equals("im:artist")) {
                            devname = parser.nextText().trim();
                            Log.d("demo", "devname" + devname);

                        }
                        else if(parser.getName().equals("im:image")) {
                            int h = Integer.parseInt(parser.getAttributeValue(null, "height"));
                            if(h == 53) {
                                simg = parser.nextText().trim();
                                Log.d("demo", "simg" + simg);
                            }
                            else if(h == 100) {
                                img = parser.nextText().trim();
                                Log.d("demo", "limg" + img);
                            }

                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("entry")){
                            podcast = new Apps( id,  appurl,  title,  devname,  date,  price,  simg);
                            podcastlist.add(podcast);
                            //Log.d("demo","podobj"+podcast);
                            podcast = null;
                        }
                    default:
                        break;
                }

                event = parser.next();
            }
            return podcastlist;
        }
    }
