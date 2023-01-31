console.log(".........publicHeader...........");
let publicHeader = new Vue({
    el: "#publicHeaderApp",
    data: {
        bookTypeList: [],
        currentAccount: ''
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

        }
    },
    created() {
        this.initBookTypeList();
        this.initCurrentAccount();
    }
});