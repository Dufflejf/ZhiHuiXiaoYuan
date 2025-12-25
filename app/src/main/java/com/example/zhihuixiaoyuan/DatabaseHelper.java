package com.example.zhihuixiaoyuan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "zhihuixiaoyuan.db";
    private static final int DATABASE_VERSION = 2;

    // 用户表
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_STUDENT_ID = "student_id";
    private static final String COL_NICKNAME = "nickname";
    private static final String COL_PASSWORD = "password";
    private static final String COL_AVATAR = "avatar";

    // 新闻表
    private static final String TABLE_NEWS = "news";
    private static final String COL_NEWS_ID = "id";
    private static final String COL_NEWS_TITLE = "title";
    private static final String COL_NEWS_CONTENT = "content";
    private static final String COL_NEWS_IMAGE = "image";
    private static final String COL_NEWS_DATE = "date";
    private static final String COL_NEWS_URL = "url";

    // 商品表
    private static final String TABLE_PRODUCTS = "products";
    private static final String COL_PRODUCT_ID = "id";
    private static final String COL_PRODUCT_NAME = "name";
    private static final String COL_PRODUCT_PRICE = "price";
    private static final String COL_PRODUCT_IMAGES = "images";
    private static final String COL_PRODUCT_PUBLISHER_ID = "publisher_id";
    private static final String COL_PRODUCT_PUBLISHER_NAME = "publisher_name";
    private static final String COL_PRODUCT_PHONE = "phone";
    private static final String COL_PRODUCT_DESCRIPTION = "description";

    // 留言表
    private static final String TABLE_COMMENTS = "comments";
    private static final String COL_COMMENT_ID = "id";
    private static final String COL_COMMENT_PRODUCT_ID = "product_id";
    private static final String COL_COMMENT_USER_ID = "user_id";
    private static final String COL_COMMENT_USER_NAME = "user_name";
    private static final String COL_COMMENT_USER_AVATAR = "user_avatar";
    private static final String COL_COMMENT_CONTENT = "content";
    private static final String COL_COMMENT_TIME = "time";

    // 失物招领表
    private static final String TABLE_LOST_FOUND = "lost_found";
    private static final String COL_LOST_FOUND_ID = "id";
    private static final String COL_LOST_FOUND_NAME = "name";
    private static final String COL_LOST_FOUND_DESCRIPTION = "description";
    private static final String COL_LOST_FOUND_PHONE = "phone";
    private static final String COL_LOST_FOUND_IMAGE = "image";
    private static final String COL_LOST_FOUND_PUBLISHER_ID = "publisher_id";
    private static final String COL_LOST_FOUND_TIME = "time";

    // 菜单表
    private static final String TABLE_MENU = "menu";
    private static final String COL_MENU_ID = "id";
    private static final String COL_MENU_NAME = "name";
    private static final String COL_MENU_IMAGE = "image";
    private static final String COL_MENU_DESCRIPTION = "description";

    // 菜单评论表
    private static final String TABLE_MENU_COMMENTS = "menu_comments";
    private static final String COL_MENU_COMMENT_ID = "id";
    private static final String COL_MENU_COMMENT_MENU_ID = "menu_id";
    private static final String COL_MENU_COMMENT_USER_ID = "user_id";
    private static final String COL_MENU_COMMENT_USER_NAME = "user_name";
    private static final String COL_MENU_COMMENT_USER_AVATAR = "user_avatar";
    private static final String COL_MENU_COMMENT_CONTENT = "content";
    private static final String COL_MENU_COMMENT_TIME = "time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_STUDENT_ID + " TEXT UNIQUE NOT NULL, " +
                COL_NICKNAME + " TEXT NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL, " +
                COL_AVATAR + " TEXT)";
        db.execSQL(createUsersTable);

        // 创建新闻表
        String createNewsTable = "CREATE TABLE " + TABLE_NEWS + " (" +
                COL_NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NEWS_TITLE + " TEXT NOT NULL, " +
                COL_NEWS_CONTENT + " TEXT, " +
                COL_NEWS_IMAGE + " TEXT, " +
                COL_NEWS_DATE + " TEXT NOT NULL, " +
                COL_NEWS_URL + " TEXT NOT NULL)";
        db.execSQL(createNewsTable);

        // 创建商品表
        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT NOT NULL, " +
                COL_PRODUCT_PRICE + " REAL NOT NULL, " +
                COL_PRODUCT_IMAGES + " TEXT, " +
                COL_PRODUCT_PUBLISHER_ID + " INTEGER NOT NULL, " +
                COL_PRODUCT_PUBLISHER_NAME + " TEXT NOT NULL, " +
                COL_PRODUCT_PHONE + " TEXT NOT NULL, " +
                COL_PRODUCT_DESCRIPTION + " TEXT)";
        db.execSQL(createProductsTable);

        // 创建留言表
        String createCommentsTable = "CREATE TABLE " + TABLE_COMMENTS + " (" +
                COL_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_COMMENT_PRODUCT_ID + " INTEGER NOT NULL, " +
                COL_COMMENT_USER_ID + " INTEGER NOT NULL, " +
                COL_COMMENT_USER_NAME + " TEXT NOT NULL, " +
                COL_COMMENT_USER_AVATAR + " TEXT, " +
                COL_COMMENT_CONTENT + " TEXT NOT NULL, " +
                COL_COMMENT_TIME + " TEXT NOT NULL)";
        db.execSQL(createCommentsTable);

        // 创建失物招领表
        String createLostFoundTable = "CREATE TABLE " + TABLE_LOST_FOUND + " (" +
                COL_LOST_FOUND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LOST_FOUND_NAME + " TEXT NOT NULL, " +
                COL_LOST_FOUND_DESCRIPTION + " TEXT, " +
                COL_LOST_FOUND_PHONE + " TEXT NOT NULL, " +
                COL_LOST_FOUND_IMAGE + " TEXT, " +
                COL_LOST_FOUND_PUBLISHER_ID + " INTEGER NOT NULL, " +
                COL_LOST_FOUND_TIME + " TEXT NOT NULL)";
        db.execSQL(createLostFoundTable);

        // 创建菜单表
        String createMenuTable = "CREATE TABLE " + TABLE_MENU + " (" +
                COL_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MENU_NAME + " TEXT NOT NULL, " +
                COL_MENU_IMAGE + " TEXT, " +
                COL_MENU_DESCRIPTION + " TEXT)";
        db.execSQL(createMenuTable);

        // 创建菜单评论表
        String createMenuCommentsTable = "CREATE TABLE " + TABLE_MENU_COMMENTS + " (" +
                COL_MENU_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MENU_COMMENT_MENU_ID + " INTEGER NOT NULL, " +
                COL_MENU_COMMENT_USER_ID + " INTEGER NOT NULL, " +
                COL_MENU_COMMENT_USER_NAME + " TEXT NOT NULL, " +
                COL_MENU_COMMENT_USER_AVATAR + " TEXT, " +
                COL_MENU_COMMENT_CONTENT + " TEXT NOT NULL, " +
                COL_MENU_COMMENT_TIME + " TEXT NOT NULL)";
        db.execSQL(createMenuCommentsTable);

        // 插入模拟新闻数据
        insertSampleNews(db);
        // 插入模拟商品数据
        insertSampleProducts(db);
        // 插入模拟失物招领数据
        insertSampleLostFound(db);
        // 插入模拟菜单数据
        insertSampleMenu(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // 创建新表
            String createLostFoundTable = "CREATE TABLE IF NOT EXISTS " + TABLE_LOST_FOUND + " (" +
                    COL_LOST_FOUND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_LOST_FOUND_NAME + " TEXT NOT NULL, " +
                    COL_LOST_FOUND_DESCRIPTION + " TEXT, " +
                    COL_LOST_FOUND_PHONE + " TEXT NOT NULL, " +
                    COL_LOST_FOUND_IMAGE + " TEXT, " +
                    COL_LOST_FOUND_PUBLISHER_ID + " INTEGER NOT NULL, " +
                    COL_LOST_FOUND_TIME + " TEXT NOT NULL)";
            db.execSQL(createLostFoundTable);

            String createMenuTable = "CREATE TABLE IF NOT EXISTS " + TABLE_MENU + " (" +
                    COL_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_MENU_NAME + " TEXT NOT NULL, " +
                    COL_MENU_IMAGE + " TEXT, " +
                    COL_MENU_DESCRIPTION + " TEXT)";
            db.execSQL(createMenuTable);

            String createMenuCommentsTable = "CREATE TABLE IF NOT EXISTS " + TABLE_MENU_COMMENTS + " (" +
                    COL_MENU_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_MENU_COMMENT_MENU_ID + " INTEGER NOT NULL, " +
                    COL_MENU_COMMENT_USER_ID + " INTEGER NOT NULL, " +
                    COL_MENU_COMMENT_USER_NAME + " TEXT NOT NULL, " +
                    COL_MENU_COMMENT_USER_AVATAR + " TEXT, " +
                    COL_MENU_COMMENT_CONTENT + " TEXT NOT NULL, " +
                    COL_MENU_COMMENT_TIME + " TEXT NOT NULL)";
            db.execSQL(createMenuCommentsTable);

            // 插入模拟数据
            insertSampleLostFound(db);
            insertSampleMenu(db);
        }
    }

    // 插入模拟新闻数据
    private void insertSampleNews(SQLiteDatabase db) {
        String[][] newsData = {
                {"2025年学生工作大会暨学生表彰大会举行", "https://www.ujs.edu.cn/__local/B/2D/B4/3733D474DC780922C9D3607F839_6E7F4471_20A93.jpg", "2025-11-23", "https://www.ujs.edu.cn/info/1062/205402.htm"},
                {"菁英学院2022级学员结业典礼暨2024级学员入院仪式举行", "https://www.ujs.edu.cn/__local/F/69/97/882603B25AB5AF582C69B9DABF0_8EA883B1_431AB.jpg", "2025-12-15", "https://www.ujs.edu.cn/info/1062/206772.htm"},
                {"学校组织党外代表人士赴合肥考察实践", "https://www.ujs.edu.cn/__local/4/75/74/4EB0CA15D041F9FE71A9D03534A_93FA6F7A_221DC.jpg", "2025-12-16", "https://www.ujs.edu.cn/info/1062/206822.htm"},
                {"中国免疫学会青年PI培训班在校举办", "https://www.ujs.edu.cn/__local/F/5F/F7/C1529280F810C69DF00C810D70B_95F3DA25_54429.png", "2024-03-04", "https://www.ujs.edu.cn/info/1062/206792.htm"},
                {"高端海洋流体装备与智能技术国家级重点实验室培育建设点建设方案论证会举行", "https://www.ujs.edu.cn/__local/8/F0/4C/6C77D6D4251CCC03F97A78E7A0C_1C0F2AC8_1B2C6.jpg", "2024-03-05", "https://www.ujs.edu.cn/info/1062/206832.htm"}
        };

        for (String[] news : newsData) {
            ContentValues values = new ContentValues();
            values.put(COL_NEWS_TITLE, news[0]);
            values.put(COL_NEWS_IMAGE, news[1]);
            values.put(COL_NEWS_DATE, news[2]);
            values.put(COL_NEWS_URL, news[3]);
            db.insert(TABLE_NEWS, null, values);
        }
    }

    // 插入模拟商品数据
    private void insertSampleProducts(SQLiteDatabase db) {
        Object[][] productData = {
                {"二手笔记本电脑", 1999.00, "https://img.alicdn.com/bao/uploaded/i4/O1CN013jVOTW1GH17tpBthV_!!4611686018427386852-53-fleamarket.heic_790x10000Q90.jpg_.webp", 1, "张三", "13800138000", "九成新，配置良好"},
                {"二手自行车", 299.00, "https://img.alicdn.com/bao/uploaded/i3/O1CN01Yd3tz524UVaNvK6Cm_!!4611686018427384594-0-fleamarket.jpg_450x10000Q90.jpg_.webp", 1, "李四", "13900139000", "八成新，功能正常"},
                {"二手教材", 50.00, "https://img.alicdn.com/bao/uploaded/i1/O1CN01EQ9H4F2JQ80sqKroa_!!4611686018427380599-0-fleamarket.jpg_450x10000Q90.jpg_.webp", 1, "王五", "13700137000", "教材齐全，无破损"},
                {"二手手机", 899.00, "https://img.alicdn.com/bao/uploaded/i3/2221364349243/O1CN01H6YO3p2I9LwnFeIdL_!!4611686018427382075-0-xy_item.jpg_450x10000Q90.jpg_.webp", 1, "赵六", "13600136000", "功能完好，外观良好"},
                {"二手台灯", 35.00, "https://img.alicdn.com/bao/uploaded/i2/O1CN018wnWT428VCe8ZNy8y_!!4611686018427387377-0-fleamarket.jpg_450x10000Q90.jpg_.webp", 1, "钱七", "13500135000", "LED台灯，亮度可调"}
        };

        for (Object[] product : productData) {
            ContentValues values = new ContentValues();
            values.put(COL_PRODUCT_NAME, (String) product[0]);
            values.put(COL_PRODUCT_PRICE, (Double) product[1]);
            values.put(COL_PRODUCT_IMAGES, (String) product[2]);
            values.put(COL_PRODUCT_PUBLISHER_ID, (Integer) product[3]);
            values.put(COL_PRODUCT_PUBLISHER_NAME, (String) product[4]);
            values.put(COL_PRODUCT_PHONE, (String) product[5]);
            values.put(COL_PRODUCT_DESCRIPTION, (String) product[6]);
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }

    // 用户相关方法
    public long insertUser(String studentId, String nickname, String password, String avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_ID, studentId);
        values.put(COL_NICKNAME, nickname);
        values.put(COL_PASSWORD, password);
        values.put(COL_AVATAR, avatar);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    public User getUserByStudentId(String studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, COL_STUDENT_ID + "=?", 
                new String[]{studentId}, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)));
            user.setStudentId(cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_ID)));
            user.setNickname(cursor.getString(cursor.getColumnIndexOrThrow(COL_NICKNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));
            int avatarIndex = cursor.getColumnIndex(COL_AVATAR);
            String avatar = avatarIndex >= 0 ? cursor.getString(avatarIndex) : null;
            user.setAvatar(avatar != null ? avatar : "");
        }
        cursor.close();
        db.close();
        return user;
    }

    public User login(String studentId, String password) {
        User user = getUserByStudentId(studentId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // 新闻相关方法
    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NEWS, null, null, null, null, null, 
                COL_NEWS_DATE + " DESC");
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_NEWS_ID)));
                news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_NEWS_TITLE)));
                news.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COL_NEWS_CONTENT)));
                news.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_NEWS_IMAGE)));
                news.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_NEWS_DATE)));
                news.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COL_NEWS_URL)));
                newsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return newsList;
    }

    // 商品相关方法
    public long insertProduct(String name, double price, String images, int publisherId, 
                             String publisherName, String phone, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_IMAGES, images);
        values.put(COL_PRODUCT_PUBLISHER_ID, publisherId);
        values.put(COL_PRODUCT_PUBLISHER_NAME, publisherName);
        values.put(COL_PRODUCT_PHONE, phone);
        values.put(COL_PRODUCT_DESCRIPTION, description);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, 
                COL_PRODUCT_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE)));
                product.setImages(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_IMAGES)));
                product.setPublisherId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_ID)));
                product.setPublisherName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_NAME)));
                product.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PHONE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_DESCRIPTION)));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> searchProducts(String keyword) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, 
                COL_PRODUCT_NAME + " LIKE ?", 
                new String[]{"%" + keyword + "%"}, null, null, COL_PRODUCT_ID + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE)));
                product.setImages(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_IMAGES)));
                product.setPublisherId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_ID)));
                product.setPublisherName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_NAME)));
                product.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PHONE)));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_DESCRIPTION)));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COL_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(id)}, null, null, null);
        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_ID)));
            product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_NAME)));
            product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRODUCT_PRICE)));
            product.setImages(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_IMAGES)));
            product.setPublisherId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_ID)));
            product.setPublisherName(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PUBLISHER_NAME)));
            product.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_PHONE)));
            product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRODUCT_DESCRIPTION)));
        }
        cursor.close();
        db.close();
        return product;
    }

    // 留言相关方法
    public long insertComment(int productId, int userId, String userName, String userAvatar, 
                              String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_COMMENT_PRODUCT_ID, productId);
        values.put(COL_COMMENT_USER_ID, userId);
        values.put(COL_COMMENT_USER_NAME, userName);
        values.put(COL_COMMENT_USER_AVATAR, userAvatar);
        values.put(COL_COMMENT_CONTENT, content);
        values.put(COL_COMMENT_TIME, time);
        long result = db.insert(TABLE_COMMENTS, null, values);
        db.close();
        return result;
    }

    public List<Comment> getCommentsByProductId(int productId) {
        List<Comment> commentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COMMENTS, null, COL_COMMENT_PRODUCT_ID + "=?", 
                new String[]{String.valueOf(productId)}, null, null, COL_COMMENT_TIME + " DESC");
        if (cursor.moveToFirst()) {
            do {
                Comment comment = new Comment();
                comment.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_COMMENT_ID)));
                comment.setProductId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_COMMENT_PRODUCT_ID)));
                comment.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_COMMENT_USER_ID)));
                comment.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(COL_COMMENT_USER_NAME)));
                comment.setUserAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COL_COMMENT_USER_AVATAR)));
                comment.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COL_COMMENT_CONTENT)));
                comment.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_COMMENT_TIME)));
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return commentList;
    }

    // 插入模拟失物招领数据
    private void insertSampleLostFound(SQLiteDatabase db) {
        String[][] lostFoundData = {
                {"丢失的黑色钱包", "在图书馆三楼丢失，内有身份证和银行卡", "13800138000", "https://img.alicdn.com/bao/uploaded/i4/O1CN017MpTlR1mmDpmwQnk9_!!4611686018427385332-0-fleamarket.jpg_450x10000Q90.jpg_.webp", "2025-12-01 10:00:00"},
                {"捡到的红色水杯", "在食堂门口捡到，请失主认领", "13900139000", "https://img.alicdn.com/bao/uploaded/i4/O1CN01uDnWbr1gWIMKybDtz_!!4611686018427380197-0-fleamarket.jpg_450x10000Q90.jpg_.webp", "2025-12-02 14:30:00"},
                {"丢失的蓝色书包", "在操场丢失，内有课本和文具", "13700137000", "https://img.alicdn.com/bao/uploaded/i4/O1CN01bLFGvY24Yd7bLDbrJ_!!4611686018427382315-0-fleamarket.jpg_450x10000Q90.jpg_.webp", "2025-12-03 09:15:00"},
                {"捡到的手机", "在教室捡到一部手机，请失主联系", "13600136000", "https://img.alicdn.com/bao/uploaded/i4/2215686515188/O1CN01KhAEt11oC9tvhQmlj_!!4611686018427382260-53-xy_item.heic_450x10000Q90.jpg_.webp", "2025-03-04 16:20:00"}
        };

        for (String[] item : lostFoundData) {
            ContentValues values = new ContentValues();
            values.put(COL_LOST_FOUND_NAME, item[0]);
            values.put(COL_LOST_FOUND_DESCRIPTION, item[1]);
            values.put(COL_LOST_FOUND_PHONE, item[2]);
            values.put(COL_LOST_FOUND_IMAGE, item[3]);
            values.put(COL_LOST_FOUND_PUBLISHER_ID, 1);
            values.put(COL_LOST_FOUND_TIME, item[4]);
            db.insert(TABLE_LOST_FOUND, null, values);
        }
    }

    // 插入模拟菜单数据
    private void insertSampleMenu(SQLiteDatabase db) {
        String[][] menuData = {
                {"宫保鸡丁", "https://img2.baidu.com/it/u=732808956,3451935454&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1067", "经典川菜，香辣可口"},
                {"红烧肉", "https://img0.baidu.com/it/u=1518191629,3308444878&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667", "肥而不腻，入口即化"},
                {"麻婆豆腐", "https://img2.baidu.com/it/u=888351201,1131482906&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1043", "麻辣鲜香，下饭神器"},
                {"糖醋里脊", "https://img1.baidu.com/it/u=4261848684,1899491253&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=649", "酸甜可口，老少皆宜"},
                {"鱼香肉丝", "https://img2.baidu.com/it/u=3090286015,1742378680&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667", "鱼香味浓，口感丰富"}
        };

        for (String[] menu : menuData) {
            ContentValues values = new ContentValues();
            values.put(COL_MENU_NAME, menu[0]);
            values.put(COL_MENU_IMAGE, menu[1]);
            values.put(COL_MENU_DESCRIPTION, menu[2]);
            db.insert(TABLE_MENU, null, values);
        }
    }

    // 失物招领相关方法
    public long insertLostFound(String name, String description, String phone, String image, int publisherId, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LOST_FOUND_NAME, name);
        values.put(COL_LOST_FOUND_DESCRIPTION, description);
        values.put(COL_LOST_FOUND_PHONE, phone);
        values.put(COL_LOST_FOUND_IMAGE, image);
        values.put(COL_LOST_FOUND_PUBLISHER_ID, publisherId);
        values.put(COL_LOST_FOUND_TIME, time);
        long result = db.insert(TABLE_LOST_FOUND, null, values);
        db.close();
        return result;
    }

    public List<LostFound> getAllLostFound() {
        List<LostFound> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOST_FOUND, null, null, null, null, null, 
                COL_LOST_FOUND_TIME + " DESC");
        if (cursor.moveToFirst()) {
            do {
                LostFound item = new LostFound();
                item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_NAME)));
                item.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_DESCRIPTION)));
                item.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_PHONE)));
                item.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_IMAGE)));
                item.setPublisherId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_PUBLISHER_ID)));
                item.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOST_FOUND_TIME)));
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // 菜单相关方法
    public List<Menu> getAllMenus() {
        List<Menu> menuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu();
                menu.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_ID)));
                menu.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_NAME)));
                menu.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_IMAGE)));
                menu.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_DESCRIPTION)));
                menuList.add(menu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuList;
    }

    public Menu getMenuById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU, null, COL_MENU_ID + "=?", 
                new String[]{String.valueOf(id)}, null, null, null);
        Menu menu = null;
        if (cursor.moveToFirst()) {
            menu = new Menu();
            menu.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_ID)));
            menu.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_NAME)));
            menu.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_IMAGE)));
            menu.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_DESCRIPTION)));
        }
        cursor.close();
        db.close();
        return menu;
    }

    // 菜单评论相关方法
    public long insertMenuComment(int menuId, int userId, String userName, String userAvatar, 
                                 String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MENU_COMMENT_MENU_ID, menuId);
        values.put(COL_MENU_COMMENT_USER_ID, userId);
        values.put(COL_MENU_COMMENT_USER_NAME, userName);
        values.put(COL_MENU_COMMENT_USER_AVATAR, userAvatar);
        values.put(COL_MENU_COMMENT_CONTENT, content);
        values.put(COL_MENU_COMMENT_TIME, time);
        long result = db.insert(TABLE_MENU_COMMENTS, null, values);
        db.close();
        return result;
    }

    public List<MenuComment> getMenuCommentsByMenuId(int menuId) {
        List<MenuComment> commentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MENU_COMMENTS, null, COL_MENU_COMMENT_MENU_ID + "=?", 
                new String[]{String.valueOf(menuId)}, null, null, COL_MENU_COMMENT_TIME + " DESC");
        if (cursor.moveToFirst()) {
            do {
                MenuComment comment = new MenuComment();
                comment.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_ID)));
                comment.setMenuId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_MENU_ID)));
                comment.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_USER_ID)));
                comment.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_USER_NAME)));
                comment.setUserAvatar(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_USER_AVATAR)));
                comment.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_CONTENT)));
                comment.setTime(cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_COMMENT_TIME)));
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return commentList;
    }
}

