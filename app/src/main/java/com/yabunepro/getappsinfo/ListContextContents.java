package com.yabunepro.getappsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ListContextContents extends AppCompatActivity {
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_context_contents);

        mTextView = findViewById(R.id.tvManifest);

        // Intentから、表示対象のアプリケーションのパッケージ名を取得
        Bundle extras = getIntent().getExtras();
        String packageName = extras.getString("packageName");
        Log.d(getPackageName(), "packageName : " + packageName);

        // パッケージに含まれるリソース種別を表示
        Context context = null;
        try {
            context = this.createPackageContext(packageName, Context.CONTEXT_RESTRICTED);
            Log.d(getPackageName(), "got context of " + context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(getPackageName(), "NameNotFoundExeption");
        }

        if (context == null) {
            return;
        }

        // リソースを取得
        Resources resources = null;
        try {
            resources = context.getResources();
            Log.d(getPackageName(), "got resources of " + context.getPackageName());
        } catch (NullPointerException e) {
            Log.d(getPackageName(), "NullPointerException");
        }

        // 画像ファイルの取得

        // レイアウトファイルの取得

        // Rawファイルの取得

        // アセットファイルの取得

        // AndroidManifest.xmlの取得
        try (XmlResourceParser xml
                     = context.getAssets().openXmlResourceParser("AndroidManifest.xml")) {
            Log.d(getPackageName(), "Got AndroidManifest.xml");

            StringBuilder manifest = new StringBuilder();
            int eventType = xml.getEventType();
            while (eventType != xml.END_DOCUMENT) {
                if (eventType == xml.START_DOCUMENT) {
                    Log.d(getPackageName(),"Start document");
                } else if (eventType == xml.START_TAG) {
                    Log.d(getPackageName(),"<" + xml.getName());
                    manifest.append("<").append(xml.getName());
                    for (int i = 0; i < xml.getAttributeCount(); i++) {
                        Log.d(getPackageName(), " " + xml.getAttributeName(i) + ":" + xml.getAttributeValue(i) + " ");
                        manifest.append(" ").append(xml.getAttributeName(i)).append(":").append(xml.getAttributeValue(i));
                    }
                    Log.d(getPackageName(), ">");
                    manifest.append(">\n");
                } else if (eventType == xml.END_TAG) {
                    Log.d(getPackageName(),"</" + xml.getName() + ">");
                    manifest.append("</").append(xml.getName()).append(">\n");
                } else if (eventType == xml.TEXT) {
                    Log.d(getPackageName(), xml.getText());
                    manifest.append(xml.getText()).append("\n");
                }
                eventType = xml.next();
            }
            mTextView.setText(manifest.toString());
        } catch(XmlPullParserException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}