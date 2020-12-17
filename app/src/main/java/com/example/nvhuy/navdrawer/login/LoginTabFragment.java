package com.example.nvhuy.navdrawer.login;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvhuy.navdrawer.MainActivity;
import com.example.nvhuy.navdrawer.R;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginTabFragment extends Fragment {
    EditText phoneNo, password;
    Button btnLogin;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_tab, container, false);
        phoneNo = root.findViewById(R.id.sign_in_pNumber);
        password = root.findViewById(R.id.sign_in_password);
        btnLogin = root.findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        return root;
    }

    private void loginUser() {
        final String userEnteredPhone = phoneNo.getText().toString().trim();
        final String userEnteredPass = password.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String systemPass = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);
                    if (systemPass.equals(userEnteredPass)) {
                        Log.i("hello", "success");
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.i("hello", "wrong");
                    }
                } else {
                    phoneNo.setError("no such user exist");
                    phoneNo.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
//    public boolean validate() {
//        boolean valid = true;
//        if (userEnteredPhone.length()>9 ){
//            phoneNo.setError("Number phone not exist");
//        }
//        if (userEnteredPass.isEmpty() || userEnteredPass.length() < 4 || userEnteredPass.length() > 10) {
//            password.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            password.setError(null);
//        }
//
//        return valid;
//    }
}
