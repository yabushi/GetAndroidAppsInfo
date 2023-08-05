package com.yabunepro.getappsinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PackageManager packageManager;
    List<ApplicationInfo> packageList;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.lvPackageName);

        // パッケージリストを取得
        packageManager = getPackageManager();
        packageList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        // パッケージリストから各パッケージの名前を取得し、表示する
        ArrayList<String> packageNameList = new ArrayList<>();
        for (int i = 0; i < packageList.size(); i++) {
            Log.d(getPackageName(), "packageName:" + packageList.get(i).packageName);
            packageNameList.add(packageList.get(i).packageName);
        }
        ArrayAdapter<String> packageNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, packageNameList);
        mListView.setAdapter(packageNameAdapter);

        // リスト項目が選択された時のイベント登録
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ListContextContents.class);

            intent.putExtra("packageName", packageList.get(position).packageName);
            startActivity(intent);
        });
    }



}