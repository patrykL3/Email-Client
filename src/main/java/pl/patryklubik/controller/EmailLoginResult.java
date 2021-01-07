package pl.patryklubik.controller;

/**
 * Create by Patryk Łubik on 06.01.2021.
 */
public enum EmailLoginResult {
    SUCCESS,
    FAILED_BY_CREDENTIALS,
    FAILED_BY_NETWORK,
    FAILED_BY_UNEXPECTED_ERROR;
}
