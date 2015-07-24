private boolean addBitmapToAlbum(Context context, Bitmap bm) {
        String uriStr = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm, "", "");
           
        SwitchLogger.d(LOG_TAG, "local album uriStr = " + uriStr);
        if(uriStr == null){
            return false;
        }
           
        String picPath  = getFilePathByContentResolver(PictureDetailActivity.this, Uri.parse(uriStr) );
        SwitchLogger.d(LOG_TAG, "begin to add to album, picture path = " + picPath);
        if(picPath == null) {
            return false;
        }
               
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues(4);
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(Images.Media.MIME_TYPE, "image/png");
        values.put(Images.Media.ORIENTATION, 0);
        values.put(Images.Media.DATA, picPath);
                  
        contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values);
           
        return true;
    }
	
	
		public  String getFilePathByContentResolver(Context context, Uri uri) {  
        if (null == uri) {  
            return null;  
        }  
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);  
        String filePath  = null;  
        if (null == c) {  
            throw new IllegalArgumentException(  
                    "Query on " + uri + " returns null result.");  
        }  
        try {  
            if ((c.getCount() != 1) || !c.moveToFirst()) {  
            } else {  
                filePath = c.getString(  
                        c.getColumnIndexOrThrow(MediaColumns.DATA));  
            }  
        } finally {  
            c.close();  
        }  
        return filePath;  
    }



add test….