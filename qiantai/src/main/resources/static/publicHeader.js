    console.log(".........publicHeader...........");
    let publicHeader = new Vue({
    el: "#publicHeaderApp",
    data: {
    bookTypeList: []
},
    methods: {
    initBookTypeList(){
    let _this = this;
    let url = "/booktype/all";
    $.get(url,function (data){
    console.log(data);
    _this.bookTypeList = data;
},"json")
}
},
    created() {
    this.initBookTypeList();
}
});