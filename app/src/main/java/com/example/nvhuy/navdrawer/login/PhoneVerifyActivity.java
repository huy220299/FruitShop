package com.example.nvhuy.navdrawer.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.nvhuy.navdrawer.MainActivity;
import com.example.nvhuy.navdrawer.R;
import com.example.nvhuy.navdrawer.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class PhoneVerifyActivity extends AppCompatActivity {


    private static final String TAG = "PhoneVerifyActivity";
    private static String uID;
    private TextView tvPhoneNumber;
    private ImageView btnBack;
    private TextView tvResend;
    private TextView tvContinue;
    private PinEntryEditText edPinEntry;
    private SweetAlertDialog pDialog;
    private SweetAlertDialog dialog;
    private DatabaseReference mDatabase;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String PHONE_NUMBER = "974124914";
    private String NAME = "";
    private String PASSWORD = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
//        getSupportActionBar().hide();


        PHONE_NUMBER = getIntent().getExtras().getString("phoneNo");
        NAME = getIntent().getExtras().getString("name");
        PASSWORD = getIntent().getExtras().getString("password");

        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        btnBack = findViewById(R.id.btnBack);
        tvResend = findViewById(R.id.tvResend);
        tvContinue = findViewById(R.id.tvContinue);
        edPinEntry = findViewById(R.id.edPinEntry);

        dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(getString(R.string.code_sent))
                .hideConfirmButton()
                .show();

        tvPhoneNumber.setText(PHONE_NUMBER);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(PHONE_NUMBER, mResendToken);
                dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitleText(getString(R.string.code_resent))
                        .hideConfirmButton()
                        .show();
            }
        });

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(mVerificationId, edPinEntry.getText().toString());
            }
        });

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
//                Toast.makeText(getApplication(), "Verify Failed!!", Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE silent]
//                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    edPinEntry.setError(getString(R.string.invalid_phone_number));
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.quota_exceeded),
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
        // [END phone_auth_callbacks]


        //Thực hiện gửi mã đi
        startPhoneNumberVerification(PHONE_NUMBER);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,             // Phone number to verify
                60,                   // Timeout duration
                TimeUnit.SECONDS,        // Unit of timeout
                this,            // Activity (for callback binding)
                mCallbacks);             // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential);
        } catch (IllegalArgumentException e) {
            Toast.makeText(PhoneVerifyActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            dialog.setTitleText(getString(R.string.login_success))
                                    .hideConfirmButton()
                                    .show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            }, 500);
                            storeNewUserData();
                            Intent intent = new Intent(PhoneVerifyActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.ERROR_TYPE);
                                dialog.setTitleText("getString(R.string.wrong_code_message)")
                                        .hideConfirmButton()
                                        .show();
                                // [END_EXCLUDE]
                            }
                        }
                    }
                });
    }

    private void storeNewUserData() {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference myRef = rootNode.getReference("Users");

        Account newUser = new Account(PHONE_NUMBER,NAME,PASSWORD);
        myRef.child(PHONE_NUMBER).setValue(newUser);
    }
    // [END sign_in_with_phone]


    @Override
    protected void onStop() {
        super.onStop();
        if (pDialog != null)
            pDialog.dismiss();
    }

}
