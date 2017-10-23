package com.realmdatabaseexample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.realmdatabaseexample.R;
import com.realmdatabaseexample.adapter.RecyclerAdapter;
import com.realmdatabaseexample.model.RealmModel;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ReturnView {
    private EditText edt_name, edt_cls, edt_sub;
    private Button btn_add, btn_view,btn_update;
    private Realm realm;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RealmResults<RealmModel> result;
    private int upadate_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        setId();
    }

    private void setId() {
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_cls = (EditText) findViewById(R.id.edt_cls);
        edt_sub = (EditText) findViewById(R.id.edt_sub);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_update = (Button) findViewById(R.id.btn_update);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(edt_name.getText().toString().trim(), edt_cls.getText().toString().trim(), edt_sub.getText().toString().trim());
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(edt_name.getText().toString().trim());
            }
        });
    }

    private void updateData(String name) {
        realm.beginTransaction();
        RealmModel db=realm.where(RealmModel.class).equalTo("id",upadate_id).findFirst();
        if (db != null) {
            db.setName(name);
            realm.copyToRealmOrUpdate(db);

        }
        realm.commitTransaction();
    }

    private void viewData() {
        result = realm.where(RealmModel.class).findAllAsync();
        result.load();
        adapter = new RecyclerAdapter(result, this, this, R.layout.item_recycler_view, 0);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void setData(final String name, final String cls, final String sub) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm rbRealm) {
                RealmModel realmModel = rbRealm.createObject(RealmModel.class, UUID.randomUUID().hashCode());
                realmModel.load();
                realmModel.setName(name);
                realmModel.setCls(cls);
                realmModel.setSub(sub);
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                System.out.println("-----------ok------------");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                System.out.println("-----------error------------" + error.getMessage());
            }
        });
    }

    @Override
    public void getactivityView(View view, final List list, final int pos, int from) {
        Button btn_del,btn_update;
        TextView tv_name,tv_cls,tv_sub;
         tv_name = (TextView) view.findViewById(R.id.tv_name);
         tv_cls = (TextView) view.findViewById(R.id.tv_cls);
         tv_sub = (TextView) view.findViewById(R.id.tv_sub);
        btn_del=(Button)view.findViewById(R.id.btn_del);
        btn_update=(Button) view.findViewById(R.id.btn_update);

        tv_name.setText(((RealmModel)list.get(pos)).getName());
        tv_cls.setText(((RealmModel)list.get(pos)).getCls());
        tv_sub.setText(((RealmModel)list.get(pos)).getSub());

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(((RealmModel)list.get(pos)).getId(),result);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 upadate_id=((RealmModel)list.get(pos)).getId();
            }
        });
    }

    public void delete(int id, final RealmResults<RealmModel> result){
        final RealmResults<RealmModel> results=realm.where(RealmModel.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm rmRealm) {
                results.deleteFromRealm(0);
            }
        });
        adapter.notifyDataSetChanged();
    }

//    private void update(int id) {
//        RealmResults<RealmModel> updateValue=realm.where(RealmModel.class).equalTo("id",id).findAll();
//
//    }
}
