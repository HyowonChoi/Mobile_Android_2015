package com.example.hw.android4weeks;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;



    // layout object
    EditText mEtName, deleteIndex;
    Button mBtInsert;
    Button mBtRead;
    Button mBtDel;
    Button mBtUpdate;
    Button mBtSort;

    ListView mList;
    ArrayAdapter<String> musicAdapter;
    ArrayList<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create databases
        db = openOrCreateDatabase(dbName,dbMode,null);
        // create table;
        createTable();

        deleteIndex = (EditText) findViewById(R.id.delete_text);
        mEtName = (EditText) findViewById(R.id.et_text);
        mBtInsert = (Button) findViewById(R.id.bt_insert);
        mBtRead = (Button) findViewById(R.id.bt_read);
        mBtDel = (Button) findViewById(R.id.bt_delete) ;
        mBtUpdate = (Button) findViewById(R.id.bt_modify);
        mBtSort = (Button) findViewById(R.id.bt_sort);


        ListView mList = (ListView) findViewById(R.id.list_view);

        mBtInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                insertData(name);
            }
        });

        mBtDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = deleteIndex.getText().toString();
                int index = Integer.parseInt(name) ;
                removeData(index);
            }
        });

        mBtUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String Index = deleteIndex.getText().toString();
                int index = Integer.parseInt(Index);
                updateData(index,name);
            }
        });

        mBtRead = (Button) findViewById(R.id.bt_read);
        mBtRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameList.clear();
                selectAll();
                musicAdapter.notifyDataSetChanged();
            }
        });

        mBtSort.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nameList.clear();
                Sorting();
                musicAdapter.notifyDataSetChanged();
            }
        });
        // Create listview
        nameList = new ArrayList<String>();
        musicAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        mList.setAdapter(musicAdapter);

    }


//    // Database 생성 및 열기
//    public void createDatabase(String dbName, int dbMode) {
//        db = openOrCreateDatabase(dbName, dbMode, null);
//    }


    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite","error: "+ e);
        }
    }

    // Table 삭제
    public void removeTable() {
        String sql = "drop table " + tableName;
        db.execSQL(sql);
    }

    // Data 추가
    public void insertData(String name) {
        String sql = "insert into " + tableName + " values(NULL, '" + name + "');";
        db.execSQL(sql);
    }

    // Data 업데이트
    public void updateData(int index, String name) {
        String sql = "update " + tableName + " set name = '" + name + "' where id = " + index + ";";
        db.execSQL(sql);
    }

    // Data 삭제
    public void removeData(int index) {
        String sql = "delete from " + tableName + " where id = " + index + ";";
        db.execSQL(sql);
    }

    // Data 읽기(꺼내오기)
    public void selectData(int index) {
        String sql = "select * from " + tableName + " where id = " + index + ";";
        Cursor result = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            int id = result.getInt(0);
            String name = result.getString(1);
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();

            Log.d("lab_sqlite", "\"index= \" + id + \" name=\" + name ");
        }
        result.close();
    }


    // 모든 Data 읽기
    public void selectAll() {
        String sql = "select * from " + tableName + ";"; // *는 전체 field를 가져오겠다는 것, select는 query지 명령어가 아니다(filed의 데이터를 리턴, 요구) db 설렉트 정렬
        Cursor results = db.rawQuery(sql, null); // cursor는 화살표. 포인터? 같은거같다 ㅋㅋ 맨처음 테이블의 결과물을 가르키고있음

        results.moveToFirst();


        while (!results.isAfterLast()) {
            int id = results.getInt(0); // 첫 번째 인덱스 값을 int형으로 얻어옴
            String name = results.getString(1); // 두 번째 인덱스 값을 string형으로 얻어옴
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name); // log.d => prinft같은 것.

            nameList.add(name);
            results.moveToNext(); // 커서를 다음으로 옮김
        }
        results.close();
    }

    public void Sorting() {
        String sql = "select * from " + tableName + " order by id desc" + ";"; // *는 전체 field를 가져오겠다는 것, select는 query지 명령어가 아니다(filed의 데이터를 리턴, 요구) db 설렉트 정렬
        Cursor results = db.rawQuery(sql, null); // cursor는 화살표. 포인터? 같은거같다 ㅋㅋ 맨처음 테이블의 결과물을 가르키고있음

        results.moveToFirst();


        while (!results.isAfterLast()) {
            int id = results.getInt(0); // 첫 번째 인덱스 값을 int형으로 얻어옴
            String name = results.getString(1); // 두 번째 인덱스 값을 string형으로 얻어옴
//            Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_LONG).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name); // log.d => prinft같은 것.

            nameList.add(name);
            results.moveToNext(); // 커서를 다음으로 옮김
        }
        results.close();
    }
}


