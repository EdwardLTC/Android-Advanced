package com.edward.myapplication.ui.news;

import static com.edward.myapplication.config.CONFIG.RSS_LINK;
import static com.edward.myapplication.config.CONFIG.RSS_LOADING;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.edward.myapplication.adapter.RSSAdapter;
import com.edward.myapplication.databinding.FragmentNewsBinding;
import com.edward.myapplication.modal.RSS;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class NewsFragment extends Fragment {
    // TODO: 9/8/2022  
    private FragmentNewsBinding binding;
    ListView lvData;
    ArrayList<String> titles;
    ArrayList<String> description;
    ArrayList<String> link;
    ArrayList<String> date;
    ArrayList<RSS> rss;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        titles = new ArrayList<>();
        description = new ArrayList<>();
        link = new ArrayList<>();
        date = new ArrayList<>();
        rss = new ArrayList<>();
        lvData = binding.lv;

        new ProcessInBackground().execute();


        lvData.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.get(i)));
            startActivity(browserIntent);
        });

        return root;
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (
                IOException e) {
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage(RSS_LOADING);
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                URL url = new URL(RSS_LINK);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                description.add(getDescription(xpp.nextText()));
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                link.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubdate")) {
                            if (insideItem) {
                                date.add(xpp.nextText());
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }

            } catch (XmlPullParserException | IOException e) {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            for (int i = 0; i < titles.size(); i++) {
                rss.add(new RSS(titles.get(i), description.get(i)));
            }
            RSSAdapter rssAdapter = new RSSAdapter(requireContext(), rss);
            lvData.setAdapter(rssAdapter);
            progressDialog.dismiss();
        }
    }

    private String getDescription(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        int check = input.lastIndexOf("br>");
        for (int j = check; j < input.length(); j++) {
            if (j >= input.lastIndexOf(check) && j <= check + 2) {
                continue;
            }
            stringBuilder.append(input.charAt(j));
        }
        return String.valueOf(stringBuilder);
    }
}