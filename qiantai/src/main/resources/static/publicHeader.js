console.log(".........publicHeader...........");
let publicHeader = new Vue({
    el: "#publicHeaderApp",
    data: {
        bookTypeList: [],
        currentAccount: '',
        searchKey: '',
    },
    methods: {
        initBookTypeList() {
            let _this = this;
            let url = "/booktype/all";
            $.get(url, function (data) {
                console.log(data);
                _this.bookTypeList = data;
            }, "json")
        },

        initCurrentAccount(){
            let _this = this;
            let url = "/user/getCurrentAccount";
            $.get(url,function (data){
                console.log(data);
                _this.currentAccount = data;
            })
        },
        toBookSearch(){
            if (this.searchKey == null || this.searchKey == ''){
                alert("请输入您要搜索的对象！");
                return;
            }
            location.href="/book/toBookSearch?searchKey="+this.searchKey;
        }
    },
    created() {
        this.initBookTypeList();
        this.initCurrentAccount();
    }
});