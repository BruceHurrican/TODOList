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

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.bruceutils.utils.logdetails.LogDetails;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import bruce.kk.brucetodos.database.bean.UnFinishItem;
import bruce.kk.brucetodos.database.dao.DaoMaster;
import bruce.kk.brucetodos.database.dao.DaoSession;
import bruce.kk.brucetodos.database.dao.UnFinishItemDao;

/**
 * Created by BruceHurrican on 16/11/26.
 */

public class MainActivityPresenter {
    private List<UnFinishItem> mDataList = new ArrayList<>(5);
    private IMainActivityView iMainActivityView;
    private Activity mActivity;

    // 数据库相关
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase writableDatabase;
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private UnFinishItemDao unFinishItemDao;

    public MainActivityPresenter(Activity activity, List<UnFinishItem> mDataList, IMainActivityView iMainActivityView) {
        this.mActivity = activity;
        this.mDataList = mDataList;
        this.iMainActivityView = iMainActivityView;
    }

    public List<UnFinishItem> initData(TodoAdapter adapter) {
        LogDetails.i("mDataList 是否为空? " + (null == mDataList));
        // 查询结果集按修改日期降序排序，如果修改日期相同则按创建日期降序排序,如果创建日期相同则按创建 id 降序排序
        mDataList = unFinishItemDao.queryBuilder().orderDesc(UnFinishItemDao.Properties.ModifyDay).orderDesc(UnFinishItemDao.Properties.CreateDay).orderDesc(UnFinishItemDao.Properties.Id).list();
        if (mDataList == null) {
            mDataList = new ArrayList<>(1);
        }
        adapter.setDataList(mDataList);
        LogDetails.d(mDataList);
        return mDataList;
    }

    public void initDatabase() {
        devOpenHelper = new DaoMaster.DevOpenHelper(mActivity, MainActivity.DB_NAME, null);
        writableDatabase = devOpenHelper.getWritableDatabase();
        daoMaster = new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
        unFinishItemDao = daoSession.getUnFinishItemDao();
    }

    /**
     * 增加一条数据
     */
    public void addItem(UnFinishItem item) {
        unFinishItemDao.insertOrReplace(item);
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        LogDetails.d(item);
        iMainActivityView.addItemCallback("有事情要做啦~");
    }

    /**
     * 修改一条数据
     */
    public void modifyItem(UnFinishItem item) {
        unFinishItemDao.update(item);
        iMainActivityView.modifyItemCallback("再怎么改也是要完成滴^v^!");
    }

    /**
     * 完成一条待办事项
     */
    public void finishedItem(UnFinishItem item) {
        unFinishItemDao.update(item);
        iMainActivityView.finishItemCallback("终于做完一件事啦~");
    }

    /**
     * 删除一条数据
     */
    public void deleteItem(UnFinishItem item) {
        unFinishItemDao.delete(item);
        iMainActivityView.deleteItemCallback("删除成功");
    }

    /**
     * 删除所有数据
     */
    public void deleteAll() {
        unFinishItemDao.deleteAll();
        iMainActivityView.deleteAllCallback("已经删除所有数据");
    }

    public void clearReference() {
        if (null != iMainActivityView) {
            iMainActivityView = null;
        }
        daoSession = null;
        devOpenHelper.close();
        devOpenHelper = null;
    }
}
