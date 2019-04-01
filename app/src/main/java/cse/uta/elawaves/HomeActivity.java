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

import androidx.navigation.Navigation;
import cse.uta.elawaves.Fragments.AccountInfoFragment;
import cse.uta.elawaves.Fragments.AddFriendFragment;
import cse.uta.elawaves.Fragments.FriendInfoFragment;
import cse.uta.elawaves.Fragments.FriendsFragment;

public class HomeActivity extends AppCompatActivity implements FriendsFragment.OnFriendSelectedListener, AddFriendFragment.OnAddFriendFragmentListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private FriendInfo current_chat_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView menu = findViewById(R.id.home_navigation);
        menu.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public void onFriendSelected(FriendInfo info) {
        this.current_chat_friend = info;
    }

    @Override
    public void onAddFriend(FriendInfo info) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.friends_item:
                Navigation.findNavController(this,R.id.nav_host_fragment_home).navigate(R.id.friendsFragment);
                break;
            case R.id.myinfo_item:
                Navigation.findNavController(this,R.id.nav_host_fragment_home).navigate(R.id.accountInfoFragment);
                break;
            case R.id.wallet_item:
                Navigation.findNavController(this,R.id.nav_host_fragment_home).navigate(R.id.accountInfoFragment);
                break;
        }
        return true;
    }
}
