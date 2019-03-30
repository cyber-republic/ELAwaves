package cse.uta.elawaves;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import org.elastos.carrier.FriendInfo;

import cse.uta.elawaves.Fragments.AddFriendFragment;
import cse.uta.elawaves.Fragments.FriendsFragment;

public class HomeActivity extends AppCompatActivity implements FriendsFragment.OnFriendSelectedListener, AddFriendFragment.OnAddFriendFragmentListener {

    private FriendInfo current_chat_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView menu = findViewById(R.id.home_navigation);

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    @Override
    public void onFriendSelected(FriendInfo info) {
        this.current_chat_friend = info;
    }

    @Override
    public void onAddFriend(FriendInfo info) {

    }
}
