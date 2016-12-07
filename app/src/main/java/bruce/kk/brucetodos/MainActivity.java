/*
 * BruceHurrican
 * Copyright (c) 2016.
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    This document is Bruce's individual learning the android demo, wherein the use of the code from the Internet, only to use as a learning exchanges.
 *    And where any person can download and use, but not for commercial purposes.
 *    Author does not assume the resulting corresponding disputes.
 *    If you have good suggestions for the code, you can contact BurrceHurrican@foxmail.com
 *    本文件为Bruce's个人学习android的作品, 其中所用到的代码来源于互联网，仅作为学习交流使用。
 *    任和何人可以下载并使用, 但是不能用于商业用途。
 *    作者不承担由此带来的相应纠纷。
 *    如果对本代码有好的建议，可以联系BurrceHurrican@foxmail.com
 */

package bruce.kk.brucetodos;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruceutils.base.BaseActivity;
import com.bruceutils.utils.ProgressDialogUtils;
import com.bruceutils.utils.logdetails.LogDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import bruce.kk.brucetodos.database.bean.UnFinishItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    /**
     * 备份数据库
     */
    public static final String DB_BACKUP = "dbBackup";
    /**
     * 恢复数据库
     */
    public static final String DB_RESTORE = "dbRestore";
    public static final String DB_NAME = "md_db";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_list)
    RecyclerView rvList;
    @Bind(R.id.content_main)
    RelativeLayout contentMain;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private MainActivityPresenter presenter;
    private List<UnFinishItem> dataList = new ArrayList<>(10);
    private TodoAdapter adapter;
    private DefaultItemAnimator defaultItemAnimator;
    private long exitFlag;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);


        presenter = new MainActivityPresenter(this, dataList, new IMainActivityViewImpl(getApplicationContext()));
        adapter = new TodoAdapter();
        presenter.initDatabase();
        dataList = presenter.initData(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(manager);

        defaultItemAnimator = new DefaultItemAnimator();
        rvList.setItemAnimator(defaultItemAnimator);

        adapter.setItemClickListener(new TodoAdapter.CardViewItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                LogDetails.i("当前选中的数据: " + dataList.get(position).content);
                modifyItem(position);
            }
        });
        adapter.setItemLongClickListener(new TodoAdapter.CardViewItemLongClickListener() {
            @Override
            public void itemLongClick(View view, int position) {
                LogDetails.i("当前选中的数据: " + dataList.get(position).content);
                UnFinishItem item = dataList.get(position);
                item.finishDay = new Date();
                presenter.finishedItem(item);
                refreshData(false);
            }
        });

        ProgressDialogUtils.initProgressBar(this, "操作中...", R.mipmap.ic_app);
        rvList.setAdapter(adapter);

        tvCount = (TextView) navView.inflateHeaderView(R.layout.nav_header_main).findViewById(R.id.tt);
        tvCount.setText("清单总数: " + dataList.size() + " 项");

        // 显示图片原来颜色
        navView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (Math.abs(exitFlag - System.currentTimeMillis()) < 2000 && exitFlag > 0) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "再按一次退出应用...", Toast.LENGTH_SHORT).show();
            exitFlag = System.currentTimeMillis();
            return;
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        if (null != presenter) {
            presenter.clearReference();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_all) {
            presenter.deleteAll();
            dataList.clear();
            refreshData(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_delete_all) {
            LogDetails.d("用户删除所有数据");
            presenter.deleteAll();
            dataList.clear();
            refreshData(false);
        } else if (id == R.id.nav_list) {
            LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
            rvList.setLayoutManager(manager);
            rvList.setItemAnimator(defaultItemAnimator);

        } else if (id == R.id.nav_grid) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            rvList.setLayoutManager(staggeredGridLayoutManager);
            rvList.setItemAnimator(defaultItemAnimator);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                showAddPop();
                break;
        }
    }

    /**
     * 刷新列表数据和统计数据
     *
     * @param isScroolToHead 是否滚动到第一条数据
     */
    private void refreshData(boolean isScroolToHead) {
        Collections.sort(dataList);
        LogDetails.i(dataList);
        adapter.notifyDataSetChanged();
        if (isScroolToHead) {
            rvList.smoothScrollToPosition(0);
        }
        tvCount.setText("清单总数: " + dataList.size() + " 项");
    }

    /**
     * 添加一条 待办事项
     */
    private void showAddPop() {
        final EditText editText = new EditText(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
//        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        editText.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        editText.setHint("写点什么吧~");
        editText.setHintTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        editText.setBackgroundColor(getResources().getColor(android.R.color.black));
        final PopupWindow popupWindow = new PopupWindow(editText, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 点击可以消失
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentMain, Gravity.CENTER, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                LogDetails.d("用户输入结果: " + editText.getText().toString());
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    ProgressDialogUtils.showProgressDialog();
                    UnFinishItem item = new UnFinishItem();
                    Date date = new Date();
                    item.createDay = date;
                    item.content = editText.getText().toString().trim();
                    item.modifyDay = date;
                    dataList.add(item);
                    refreshData(true);
                    presenter.addItem(item);
                }
            }
        });
    }

    /**
     * 操作单项数据
     *
     * @param position
     */
    private void modifyItem(final int position) {
        final UnFinishItem item = dataList.get(position);
        LogDetails.d(item);
        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(getResources().getColor(android.R.color.black));

        final EditText editText = new EditText(MainActivity.this);
        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        editText.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        editText.setHint(item.content);
        editText.setHintTextColor(getResources().getColor(android.R.color.holo_orange_dark));

        linearLayout.addView(editText);

        Button btnModify = new Button(MainActivity.this);
        btnModify.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnModify.setText("修改");
        btnModify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnModify.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
        btnModify.setBackgroundColor(getResources().getColor(android.R.color.black));

        linearLayout.addView(btnModify);

        Button btnDeleteItem = new Button(MainActivity.this);
        btnDeleteItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnDeleteItem.setText("删除");
        btnDeleteItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        btnDeleteItem.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
        btnDeleteItem.setBackgroundColor(getResources().getColor(android.R.color.black));

        linearLayout.addView(btnDeleteItem);

        final PopupWindow popupWindow = new PopupWindow(linearLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 点击可以消失
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(contentMain, Gravity.CENTER, 0, 0);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogDetails.d("用户输入结果: " + editText.getText().toString());
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    Date date = new Date();
                    item.content = editText.getText().toString().trim();
                    item.modifyDay = date;
                    item.finishDay = null;
                    ProgressDialogUtils.showProgressDialog();
                    presenter.modifyItem(item);
                    dataList.set(position, item);
                    refreshData(true);
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "您未修改内容~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.remove(position);
                presenter.deleteItem(item);
                refreshData(false);
                popupWindow.dismiss();
            }
        });
    }
}
