package org.csu.mypetstore.controller;


import jakarta.servlet.http.HttpSession;
import org.csu.mypetstore.entity.Account;
import org.csu.mypetstore.entity.LogSheet;
import org.csu.mypetstore.entity.SignOn;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.LogService;
import org.csu.mypetstore.vo.CartVO;
import org.csu.mypetstore.vo.SignVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
//用户Controller

@Controller
@RequestMapping("/accountFor")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;
    @Autowired
    private LogService logService;

    @GetMapping("signon")
    public String signOn() {
        return "account/signon";

    }

    @PostMapping("getSignon")//登陆成功
    public String signOnForMain(String username, String password, HttpSession session) {
        SignVO signVO = accountService.getSignON(username, password);
        CartVO cartVO = cartService.getAccountCart(username);
        String view = null;
        int flag = 0;
        if (signVO.getLoginAccount() == null) {
            view = "/account/signon";

        } else {
            session.setAttribute("userInfo", signVO);
            session.setAttribute("cart", cartVO);
            session.setAttribute("flag", flag);
            view = "/catalog/main";
        }
        return view;
    }

    @GetMapping("signOut")
    public String SignOut(HttpSession session) {
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        CartVO cartVO = (CartVO) session.getAttribute("cart");
        signVO = null;
        cartVO = null;
        session.setAttribute("userInfo", signVO);
        session.setAttribute("cart", cartVO);
        return "/catalog/main";

    }

    @GetMapping("edit")
    public String edit() {
        return "/account/editAccountForm";

    }

    @PostMapping("saveAccount")
    public String saveAccount(Account account, SignOn signOn, HttpSession session) {
        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        String username = signVO.getLoginAccount().getUsername();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String email = account.getEmail();
        String phone = account.getPhone();
        String address1 = account.getAddress1();
        String address2 = account.getAddress2();
        String city = account.getCity();
        String state = account.getState();
        String zip = account.getZip();
        String country = account.getCountry();
        Account newAccount = signVO.getLoginAccount();
        newAccount.setUsername(username);
        newAccount.setFirstName(firstName);
        newAccount.setCity(city);
        newAccount.setCountry(country);
        newAccount.setEmail(email);
        newAccount.setState(state);
        newAccount.setPhone(phone);
        newAccount.setZip(zip);
        newAccount.setAddress1(address1);
        newAccount.setAddress2(address2);
        newAccount.setLastName(lastName);
        signOn.setUsername(username);
        System.out.println(signOn.getUsername());
        SignVO signVO2 = accountService.updateAccountPassword(signOn);
        SignVO signVO1 = accountService.updateAccount(newAccount);
        return "/account/editAccountForm";
    }

    @GetMapping("register")
    public String registerForm() {
        return "/account/register";
    }

    @PostMapping("registerAccount")//注册用户
    public String registerAccount(Account account, SignOn signOn) {
        SignVO signVO2 = accountService.InsertSignOn(signOn);
        SignVO signVO1 = accountService.InsertAccount(account);
        return "/catalog/main";
    }

    @GetMapping("SearchRecord")
    public String Record(HttpSession session) {

        SignVO signVO = (SignVO) session.getAttribute("userInfo");
        String username = signVO.getLoginAccount().getUsername();
        List<LogSheet> recordList = logService.searchRecord(username);
        session.setAttribute("recordList", recordList);
        return "/account/Record";
    }
}

//public class SignOnServlet extends HttpServlet {
//
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.username = req.getParameter("username");
//        this.password = req.getParameter("password");
//        System.out.println(username);
//        //校验用户输入的正确性
//        String value1=req.getParameter("vCode");
//        /*获取图片的值*/
//        HttpSession session=req.getSession();
//        String value2=(String)session.getAttribute("checkcode");
//        Boolean isSame = false;
//        /*对比两个值（字母不区分大小写）*/
//        if(value2.equalsIgnoreCase(value1)){
//            isSame = true;
//        }
//        if(!validate()){
//            req.setAttribute("signOnMsg", this.msg);
//            req.getRequestDispatcher(SIGN_ON_FORM).forward(req,resp);
//        }else{
//            AccountService accountService = new AccountService();
//            Cart cart = new Cart();
//            System.out.println(cart);
//            Account loginAccount = accountService.getAccount(username, password);
//            if(loginAccount == null){
//                this.msg = "用户名或密码错误";
//                req.getRequestDispatcher(SIGN_ON_FORM).forward(req,resp);
//            }else if(!isSame){
//                this.msg = "验证码错误";
//                req.getRequestDispatcher(SIGN_ON_FORM).forward(req,resp);
//            } else {
//                loginAccount.setPassword(null);
//                session = req.getSession();
//                cart.initCart(loginAccount.getUsername());
//                session.setAttribute("cart", cart);
//                session.setAttribute("loginAccount" , loginAccount);
//
//                if(loginAccount.isListOption()){
//                    CatalogService catalogService = new CatalogService();
//                    List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
//                    session.setAttribute("myList", myList);
//                }
//
//                resp.sendRedirect("mainStyle");
//            }
//        }
//    }
//
//    private boolean validate(){
//        if(this.username == null || this.username.equals("")){
//            this.msg = "用户名不能为空";
//            return false;
//        }
//        if(this.password == null || this.password.equals("")){
//            this.msg = "密码不能为空";
//            return false;
//        }
//        return true;
//    }
//
//}
