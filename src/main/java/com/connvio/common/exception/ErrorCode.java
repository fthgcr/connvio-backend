package com.connvio.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // Genel Hatalar / General Errors
    INTERNAL_SERVER_ERROR(1000, "Sunucu hatası", "Internal server error"),
    INVALID_REQUEST(1001, "Geçersiz istek", "Invalid request"),
    
    // Kullanıcı Hataları / User Errors
    USER_NOT_FOUND(2000, "Kullanıcı bulunamadı", "User not found"),
    USERNAME_ALREADY_EXISTS(2001, "Bu kullanıcı adı zaten kullanılıyor", "Username already exists"),
    INVALID_CREDENTIALS(2002, "Geçersiz kullanıcı adı veya şifre", "Invalid username or password"),
    
    // Sunucu Hataları / Server Errors
    SERVER_NOT_FOUND(3000, "Sunucu bulunamadı", "Server not found"),
    SERVER_ALREADY_EXISTS(3001, "Bu isimde bir sunucu zaten var", "Server already exists"),
    NOT_SERVER_OWNER(3002, "Bu işlem için sunucu sahibi olmanız gerekiyor", "Must be server owner"),
    SERVER_NAME_REQUIRED(3003, "Sunucu ismi girmek zorunludur.", "Server name required."),
    
    // Yetkilendirme Hataları / Authorization Errors
    UNAUTHORIZED(4000, "Bu işlem için yetkiniz yok", "Unauthorized"),
    INVALID_TOKEN(4001, "Geçersiz veya süresi dolmuş token", "Invalid or expired token"),
    
    // Kanal Hataları / Channel Errors
    CHANNEL_NOT_FOUND(4000, "Kanal bulunamadı", "Channel not found"),
    CHANNEL_ALREADY_EXISTS(4001, "Bu isimde bir kanal zaten var", "Channel already exists"),
    CHANNEL_NAME_REQUIRED(4002, "Kanal ismi zorunludur", "Channel name is required"),
    ALREADY_SERVER_MEMBER(4003, "Kullanıcı halihazırda bu sunucunun üyesi", "The user is already a member of this server"),
    INVALID_INVITE_CODE(4004, "Geçersiz davet kodu", "Invalid invitation code"),
    NOT_SERVER_MEMBER(4005, "Kullanıcı halihazırda bu sunucunun üyesi", "The user is already a member of this server"),

    // Mesaj Hataları / Message Errors
    MESSAGE_NOT_FOUND(5000, "Mesaj bulunamadı", "Message not found"),
    MESSAGE_TOO_LONG(5001, "Mesaj çok uzun", "Message too long"),
    EMPTY_MESSAGE(5002, "Boş mesaj gönderilemez", "Empty message not allowed");

    private final int code;
    private final String turkishMessage;
    private final String englishMessage;

    ErrorCode(int code, String turkishMessage, String englishMessage) {
        this.code = code;
        this.turkishMessage = turkishMessage;
        this.englishMessage = englishMessage;
    }

} 