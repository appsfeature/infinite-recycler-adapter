# Infinite-recycler-adapter

An Android Library that dynamically load more items when scroll to end with bottom ProgressBar

<p align="center">
  <img src="https://raw.githubusercontent.com/appsfeature/infinite-recycler-adapter/master/screenshots/preview_sample.gif" alt="Preview 1" width="300" />
</p>

## Setup Project

Add this to your project build.gradle

Project-level build.gradle (<project>/build.gradle):

``` gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

Add this to your project build.gradle

Module-level build.gradle (<module>/build.gradle):

#### [![](https://jitpack.io/v/appsfeature/infinite-recycler-adapter.svg)](https://jitpack.io/#appsfeature/infinite-recycler-adapter)
```gradle

dependencies {
    implementation 'com.github.appsfeature:infinite-recycler-adapter:x.y'
}
```

In your activity class:
#### Usage method
```java
public class ExampleActivity extends AppCompatActivity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContactAdapter mAdapter = new ContactAdapter(contacts, this);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new OnLoadMoreItems() {
            @Override
            public void onLoadMore() {
                getMoreDataFromServer();
            }
        });
    } 
    
    private void getMoreDataFromServer() {
        //after getting response from server
        List<String> response = new ArrayList<>();//"Getting data successfully"
        if(response!=null && response.size()>0){
            mAdapter.finish();
        }else {
            mAdapter.stop();
        }
    }


}

```

In your Adapter class:
#### Usage method
```java
public class ContactAdapter extends LoadMoreAdapter {
    private Activity activity;
    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts, Activity activity) {
        super(contacts, R.layout.item_loading);
        this.contacts = contacts;
        this.activity = activity;
    }

    @Override
    protected RecyclerView.ViewHolder onAbstractCreateViewHolder(ViewGroup parent, int var2) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_recycler_view_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    protected void onAbstractBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Contact contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.phone.setText(contact.getEmail());
            userViewHolder.email.setText(contact.getPhone());
        }
    }


    private class UserViewHolder extends RecyclerView.ViewHolder {
        TextView phone;
        TextView email;

        UserViewHolder(View view) {
            super(view);
            phone = view.findViewById(R.id.txt_phone);
            email = view.findViewById(R.id.txt_email);
        }
    }
}

```

#### Useful Links:
1. https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper
