    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        //如果convertView对象为空则创建新对象，不为空则复用  
        if (convertView == null) {
            convertView = inflater.inflate(..., null);
            // 创建 ViewHodler 对象  
            vHolder = new ViewHolder();
            vHolder.img= (ImageView) convertView.findViewById(...);
            vHolder.tv= (TextView) convertView.findViewById(...);
            // 将ViewHodler保存到Tag中(Tag可以接收Object类型对象，所以任何东西都可以保存在其中)
            convertView.setTag(vHolder);
        } else {
            //当convertView不为空时，通过getTag()得到View  
            vHolder = (ViewHolder) convertView.getTag();
        }
        // 给对象赋值，修改显示的值  
        vHolder.img.setImageBitmap(...);
        vHolder.tv.setText(...);
        return convertView;
    }
    //将显示的View 包装成类  
    static class ViewHolder {
        TextView tv;
        ImageView img;
    }