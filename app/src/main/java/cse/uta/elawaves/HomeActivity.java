package cse.uta.elawaves;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.view.View;



import org.elastos.carrier.FriendInfo;

import cse.uta.elawaves.Fragments.AccountInfoFragment;
import cse.uta.elawaves.Fragments.AddFriendFragment;
import cse.uta.elawaves.Fragments.FriendInfoFragment;
import cse.uta.elawaves.Fragments.FriendsFragment;

public class HomeActivity extends AppCompatActivity implements FriendsFragment.OnFriendSelectedListener, AddFriendFragment.OnAddFriendFragmentListener {

    private FriendInfo current_chat_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView menu = findViewById(R.id.home_navigation);
        menu.setOnNavigationItemSelectedListener(navListener);


    }

    @Override
    public void onFriendSelected(FriendInfo info) {
        this.current_chat_friend = info;
    }

    @Override
    public void onAddFriend(FriendInfo info) {

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch(menuItem.getItemId()) {
                case R.id.friends_item:
                    selectedFragment = new FriendsFragment();
                    break;
                case R.id.myinfo_item:
                    selectedFragment = new AccountInfoFragment();
                    break;
                case R.id.wallet_item:
                    selectedFragment = new AccountInfoFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;

            }

    };

}
