package com.spectra.fieldforce.Model;

public class SavetokenRequest {


        String Authkey="";

        String Action="";
        String user_name="";
        String user_token="";

        public String getAuthkey () {
            return Authkey;
        }

        public void setAuthkey ( String authkey ) {
            Authkey = authkey;
        }

        public String getAction () {
            return Action;
        }

        public void setAction ( String action ) {
            Action = action;
        }

        public String getEmail () {
            return user_name;
        }

        public void setEmail ( String user_name ) {
            this.user_name = user_name;
        }

        public String getUser_token () {
            return user_token;
        }

        public void setUser_token ( String user_token ) {
            this.user_token = user_token;
        }

}
