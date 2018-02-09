# EditText

1.输入监听(只关心输入内容的变化)

```Java

private StringBuilder stringBuilder = new StringBuilder();//将保存输入内容，与输入框保持一致
private int mStart = 0;
private int mAfter = 0;//大于0(有新增或替换)
private int mBefore = 0;//大于0(有删除)
private int mCount = 0;

addTextChangedListener(new TextWatcher(){
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					Log.e("", "onTextChanged:" + s.toString() + ", start=" + start + ", count=" + count + ", before=" + before);
					mStart = start;
					mBefore = before;
					if(mBefore > 0 && count > 0){
						mCount = count;
					}else{
						mCount = 0;
					}
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					Log.e("", "beforeTextChanged:" + s.toString() + ", start=" + start + ", count=" + count + ", after=" + after);
					mAfter = after;
					if(mAfter > 0 && count > 0){
						mCount = count;
					}else{
						mCount = 0;
					}
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					Log.e("", "afterTextChanged:" + s.toString());
					if(0 == mCount){//删除或者新增
						if(mBefore > 0){//删除
							PrintLog.printLogE(TAG, "input=" + stringBuilder.toString());
							stringBuilder.delete(mStart, mStart + mBefore);
							PrintLog.printLogE(TAG, "input=" + stringBuilder.toString());
						}else if(mAfter > 0){//新增
							String content = s.toString();
							String add = content.substring(mStart, mStart + mAfter);
							stringBuilder.insert(mStart, add);
							PrintLog.printLogE(TAG, "add=" + add + ", input=" + stringBuilder.toString());
						}
					}else{
						if(mAfter > 0 && mBefore > 0){//替换(复制粘贴)
							//先执行删除
							PrintLog.printLogE(TAG, "input=" + stringBuilder.toString());
							stringBuilder.delete(mStart, mStart + mBefore);
							PrintLog.printLogE(TAG, "input=" + stringBuilder.toString());
							
							//后处理新增
							String content = s.toString();
							String add = content.substring(mStart, mStart + mAfter);
							stringBuilder.insert(mStart, add);
							PrintLog.printLogE(TAG, "add=" + add + ", input=" + stringBuilder.toString());
						}
					}
				}
			});
   
   1. 看日志，总结
      当after>0  before=0时,是新增;
      当after=0 before>0时，是删除;
      当after>0 before>0时，是替换;
   
   
    删除操作
    02-09 15:34:16.000: E/(11379): beforeTextChanged:呵呵看看片, start=3, count=1, after=0
    02-09 15:34:16.003: E/(11379): onTextChanged:呵呵看片, start=3, count=0, before=1
    02-09 15:34:16.007: E/(11379): afterTextChanged:呵呵看片
    
    输入新增时(含粘贴)
    02-09 15:34:28.498: E/(11379): beforeTextChanged:呵呵看片, start=3, count=0, after=1
    02-09 15:34:28.523: E/(11379): onTextChanged:呵呵看机片, start=3, count=1, before=0
    02-09 15:34:28.535: E/(11379): afterTextChanged:呵呵看机片
    
    替换操作(复制粘贴)
    02-09 16:59:58.728: E/(19476): beforeTextChanged:墨乐监 控快 乐, start=4, count=2, after=6
    02-09 16:59:58.730: E/(19476): onTextChanged:墨乐监 墨乐监控快乐 乐, start=4, count=6, before=2
    02-09 16:59:58.731: E/(19476): afterTextChanged:墨乐监 墨乐监控快乐 乐
   
   2. 方法介绍
   public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      该方法在文本改变之前调用，传入了四个参数：
      CharSequence s：文本改变之前的内容 
      int start：文本开始改变时的起点位置，从0开始计算 
      int count：要被改变的文本字数，即将要被替代的选中文本字数 
      int after：改变后添加的文本字数，即替代选中文本后的文本字数 
      该方法调用是在文本没有被改变，但将要被改变的时候调用，把四个参数组成一句话就是： 
      在当前文本s中，从start位置开始之后的count个字符（即将）要被after个字符替换掉
      
      public void onTextChanged(CharSequence s, int start, int before, int count) {}
        该方法是在当文本改变时被调用，同样传入了四个参数：
        CharSequence s：文本改变之后的内容 
        int start：文本开始改变时的起点位置，从0开始计算 
        int before：要被改变的文本字数，即已经被替代的选中文本字数 
        int count：改变后添加的文本字数，即替代选中文本后的文本字数 
        该方法调用是在文本被改变时，改变的结果已经可以显示时调用，把四个参数组成一句话就是： 
        在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了

      public void afterTextChanged(Editable s) {}
        该方法是在文本改变结束后调用，传入了一个参数：
        Editable s：改变后的最终文本 
        该方法是在执行完beforeTextChanged、onTextChanged两个方法后才会被调用，此时的文本s为最终显示给用户看到的文本。我们可以再对该文本进行下一步处理，比如把文本s显示在UI界面上



   
```
