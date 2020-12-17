package com.example.nvhuy.navdrawer.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvhuy.navdrawer.R;

public class SignupTabFragment extends Fragment {
    EditText _phoneNo, _name, _password, _confirmPassword;
    Button btnSignUp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_signup_tab, container, false);

        _phoneNo = root.findViewById(R.id.sign_up_number);
        _name = root.findViewById(R.id.sign_up_name);
        _password = root.findViewById(R.id.sign_up_password);
        _confirmPassword = root.findViewById(R.id.sign_up_confirm_password);
        btnSignUp = root.findViewById(R.id.sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhoneVerifyActivity.class);
                String phoneNumber = _phoneNo.getText().toString().trim();
                if (phoneNumber.charAt(0) == '0') {
                    phoneNumber = phoneNumber.substring(1);
                }
                intent.putExtra("phoneNo", "+84" + phoneNumber);
                intent.putExtra("name",_name.getText().toString());
                intent.putExtra("password",_password.getText().toString());
                startActivity(intent);

               // Toast.makeText(getActivity().getApplicationContext(),"button click",Toast.LENGTH_LONG).show();
            }
        });



        return root;
    }
}
