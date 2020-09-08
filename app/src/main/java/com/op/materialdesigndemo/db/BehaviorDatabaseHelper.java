package com.op.materialdesigndemo.db;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.op.materialdesigndemo.entity.Story;
import com.op.materialdesigndemo.entity.UserBehavior;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BehaviorDatabaseHelper {
    private static final String TAG = "DbHelper";
    private static volatile BehaviorDatabaseHelper instance;
    private StoryDao storyDao;
    private UserBehaviorDao userBehaviorDao;
    private ExecutorService workerThread;
    private StoryDaoHelper storyDaoHelper;
    private UserBehaviorDaoHelper userBehaviorDaoHelper;

    public static BehaviorDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (BehaviorDatabaseHelper.class) {
                if (instance == null) {
                    instance = new BehaviorDatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    private BehaviorDatabaseHelper(Context context) {
        BehaviorDatabase behaviorDatabase = BehaviorDatabase.create(context.getApplicationContext());
        storyDao = behaviorDatabase.getStoryDao();
        userBehaviorDao = behaviorDatabase.getUserBehaviorDao();
        workerThread = Executors.newCachedThreadPool();
        userBehaviorDaoHelper = new UserBehaviorDaoHelper();
        storyDaoHelper = new StoryDaoHelper();
    }

    public void init() {
        Log.d(TAG, "database is created");
    }

    private void checkThread() {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            throw new IllegalThreadStateException("Database can not be accessed in main thread");
        }
    }

    public UserBehavior getUserBehaviorById(int id) {
        return userBehaviorDaoHelper.getBehaviorById(id);
    }

    public Story getStoryById(int id) {
        return storyDaoHelper.getStoryById(id);
    }

    public int getStoryCount() {
        return storyDaoHelper.getStoryCount();
    }

    public void insertOrReplaceStory(Story... stories) {
        storyDaoHelper.insert(stories);
    }

    public void insertOrReplaceStory(List<Story> stories) {
        storyDaoHelper.insert(stories);
    }

    public List<Story> getStoryDuring(long startTime, long endTime) {
        return storyDaoHelper.getStoryDuring(startTime, endTime);
    }

    public int getUserBehaviorCount() {
        return userBehaviorDaoHelper.getBehaviorCount();
    }

    public void insertOrReplaceBehavior(UserBehavior... userBehaviors) {
        userBehaviorDaoHelper.insert(userBehaviors);
    }

    public List<UserBehavior> getUserBehaviorDuring(long startTime, long endTime) {
        return userBehaviorDaoHelper.getBehaviorsDuring(startTime, endTime);
    }

    private class StoryDaoHelper implements StoryDao {

        @Override
        public List<Story> getStoryDuring(long startTime, long endTime) {
            checkThread();
            return storyDao.getStoryDuring(startTime, endTime);
        }

        @Override
        public int getStoryCount() {
            checkThread();
            return storyDao.getStoryCount();
        }

        @Override
        public Story getStoryById(int id) {
            checkThread();
            return storyDao.getStoryById(id);
        }

        @Override
        public void insert(final Story... stories) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    storyDao.insert(stories);
                }
            });
        }

        @Override
        public void insert(final List<Story> stories) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    storyDao.insert(stories);
                }
            });
        }

        @Override
        public void update(final Story... stories) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    storyDao.update(stories);
                }
            });
        }

        @Override
        public void delete(final Story... stories) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    storyDao.delete(stories);
                }
            });
        }
    }

    private class UserBehaviorDaoHelper implements UserBehaviorDao {

        @Override
        public List<UserBehavior> getBehaviorsDuring(long startTime, long endTime) {
            checkThread();
            return userBehaviorDao.getBehaviorsDuring(startTime, endTime);
        }

        @Override
        public int getBehaviorCount() {
            checkThread();
            return userBehaviorDao.getBehaviorCount();
        }

        @Override
        public UserBehavior getBehaviorById(int id) {
            checkThread();
            return userBehaviorDao.getBehaviorById(id);
        }

        @Override
        public void insert(final UserBehavior... behaviors) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    userBehaviorDao.insert(behaviors);
                }
            });
        }

        @Override
        public void update(final UserBehavior... behaviors) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    userBehaviorDao.update(behaviors);
                }
            });
        }

        @Override
        public void delete(final UserBehavior... behaviors) {
            workerThread.execute(new Runnable() {
                @Override
                public void run() {
                    userBehaviorDao.delete(behaviors);
                }
            });
        }
    }
}
