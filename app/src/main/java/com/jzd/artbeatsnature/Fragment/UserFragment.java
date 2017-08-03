package com.jzd.artbeatsnature.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jzd.artbeatsnature.Activity.ChangeActivity;
import com.jzd.artbeatsnature.Activity.RecordActivity;
import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.Activity.MainActivity;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.Base.BaseFragment;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.SqlUtils.SqlHelper;

/**
 * Created by Wxd on 2017-07-13.
 */

public class UserFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private View record, changepsd;
    private TextView name, account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userfragment, null);
        intView();
        return view;

    }

    private void intView() {
        View titel = view.findViewById(R.id.titel);
        View outlogin = view.findViewById(R.id.outlogin);
        setMaginTop(titel);
        record = view.findViewById(R.id.record);
        name = (TextView) view.findViewById(R.id.name);
        account = (TextView) view.findViewById(R.id.account);
        record.setOnClickListener(this);
        changepsd = view.findViewById(R.id.changepsd);
        changepsd.setOnClickListener(this);
        outlogin.setOnClickListener(this);
        name.setText(MyApp.getInstance().getUserbean().getUserName());
        account.setText(MyApp.getInstance().getUserbean().getJobNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.outlogin:
                SqlHelper dao = new SqlHelper(getActivity(), MyApp.getInstance().getSqlCode(), "UserInfor");
                dao.delete();
                MyApp.getInstance().setUserBean(null);
                ((MainActivity) getActivity()).GotoLogin();
                break;
            case R.id.record:
                startActivity(new Intent(getActivity(), RecordActivity.class));
                break;
            case R.id.changepsd:
                startActivity(new Intent(getActivity(), ChangeActivity.class));
                break;
        }
    }
}
