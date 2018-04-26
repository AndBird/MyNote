# NotificationManager

```
//隐藏状态栏
private void hideNotification(){
  try {
    NotificationManager manager = (NotificationManager) m_Context.getSystemService(Context.NOTIFICATION_SERVICE);
    manager.cancel(Notification_Id);
  } catch (Exception e) {
    e.printStackTrace();
  }
}
	
//显示状态栏
private void showNotification(String title, int progress, int maxProgress) {
  try {
    NotificationManager manager = (NotificationManager) m_Context.getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(m_Context);
    //RemoteViews views = new RemoteViews(m_Context.getPackageName(), R.layout.upgrade_notification_layout);
    //views.setImageViewResource(R.id.notification_large_icon, R.drawable.ic_launcher);
    //views.setTextViewText(R.id.notification_title, title);
    //views.setTextViewText(R.id.notification_text, content);

    mBuilder.setContentTitle(title);
    if(progress == maxProgress){
      mBuilder.setContentText(m_Context.getString(R.string.download_click_install));
    }else{
      mBuilder.setContentText(m_Context.getString(R.string.downloading));
    }
    // 第一次提示消息的时候显示在通知栏上
    mBuilder.setTicker(title);
    mBuilder.setSmallIcon(R.drawable.ic_launcher);
    // 自己维护通知的消失
    mBuilder.setAutoCancel(true);
     //设置为不可清除模式
    mBuilder.setOngoing(true);
    //采用自定义view
    //mBuilder.setContent(views);
    //views.setProgressBar(R.id.progressBar, maxProgress, progress, false);
    mBuilder.setProgress(maxProgress, progress, false);

    if(progress == maxProgress){
      File apkfile = new File(apkFilePath);
      if (apkfile.exists()) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(m_Context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
      }
    }

    manager.notify(Notification_Id, mBuilder.build());
  } catch (Exception e) {
    e.printStackTrace();
  }
}

```
