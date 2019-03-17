package server;

/**
 * 何故か空のリクエストが飛んでくるときがあるので、それを回避するための例外
 */
public class EmptyRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
