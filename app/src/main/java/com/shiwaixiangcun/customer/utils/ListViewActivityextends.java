//package com.shiwaixiangcun.customer.util;
//
//import android.app.Activity;
//
//public class ListViewActivity extends Activity {
//
//    /** Called when the activity is first created. */
//
//    @Override
//
//    public void onCreate(Bundle savedInstanceState){
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.main);
//
//        Button button=(Button)findViewById(R.id.button);
//
//       //获取ListView
//
//        final LayoutInflater factory = LayoutInflater.from(ListViewActivity.this);
//
//                 final View view = factory.inflate(
//
//                         R.layout.listview,null);
//
//        final ListView list = (ListView) view.findViewById(R.id.ListView01);
//
//        //把数据项添加到listItem里面
//
//        ArrayList<HashMap<String,Object>> listItem =newArrayList<HashMap<String, Object>>();
//
//        for(int i=0;i<5;i++)
//
//        {
//
//        if(i==0){
//
//             HashMap<String,Object> map =new HashMap<String,Object>();
//
//                map.put("ItemImage", R.drawable.checked);
//
//                map.put("ItemTitle", "1");
//
//                listItem.add(map);
//
//        }else if(i==1){
//
//             HashMap<String,Object> map =new HashMap<String,Object>();
//
//                map.put("ItemImage", R.drawable.c);
//
//                map.put("ItemTitle", "2");
//
//                listItem.add(map);
//
//        }else if(i==2){
//
//             HashMap<String,Object> map =new HashMap<String,Object>();
//
//                map.put("ItemImage", R.drawable.d);
//
//                map.put("ItemTitle", "3");
//
//                listItem.add(map);
//
//        }else if(i==3){
//
//             HashMap<String,Object> map =new HashMap<String,Object>();
//
//                map.put("ItemImage", R.drawable.d);
//
//                map.put("ItemTitle", "4");
//
//                listItem.add(map);
//
//        }else{
//
//             HashMap<String,Object> map =new HashMap<String,Object>();
//
//                map.put("ItemImage", R.drawable.e);
//
//                map.put("ItemTitle", "5");
//
//                listItem.add(map);
//
//        }
//
//        }
//
//       //获得SimpleAdapter，并且把它添加到listView中
//
//        SimpleAdapter listItemAdapter =new SimpleAdapter(this,listItem,
//
//               R.layout.item,
//
//               new String[] {"ItemImage","ItemTitle"},
//
//               new int[] {R.id.imageView,R.id.checkedTextView}
//
//           );
//
//
//
//           list.setAdapter(listItemAdapter);
//
//           list.setOnItemClickListener(new OnItemClickListener(){
//
//
//
//                public void onItemClick(AdapterView<?>arg0, View arg1,int arg2,
//
//                        long arg3) {
//
//              //把所有的单选全部设为非选中
//
//                 for(int i=0;i<arg0.getCount();i++)
//
//                 {
//                    View v = list.getChildAt(i);
//
//                    CheckedTextViewcheckText=(CheckedTextView)v.findViewById(R.id.checkedTextView);
//
//                    checkText.setChecked(false);
//
//                 }
//              //获得点击项的CheckedTextView，并设为选中
//                 CheckedTextViewcheck=(CheckedTextView)arg1.findViewById(R.id.checkedTextView);
//                  check.setChecked(true);
//               }
//           });
//
//           final AlertDialog.Builder builder=new AlertDialog.Builder(ListViewActivity.this);
//           button.setOnClickListener(new View.OnClickListener() {
//              public void onClick(View v) {
//                  builder.setTitle("Dialog");
//                  builder.setView(list);
//               builder.setNegativeButton("cencel",null);
//               builder.create().show();
//              }
//           });
//    }
//}