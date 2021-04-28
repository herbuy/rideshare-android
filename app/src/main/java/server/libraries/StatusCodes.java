package server.libraries;


public class StatusCodes {

    public static class ClientError {

        public static final int IllegalArgument = 400;
        public static final int LoginRequired = 401;
        public static final int NotPermitted = 403;
        public static final int NotExist = 404;
        public static final int Timeout = 408;
        public static final int AlreadyExists = 409;
    }
    public static class ServerError{
        public static final int InternalError = 500;
    }
}

