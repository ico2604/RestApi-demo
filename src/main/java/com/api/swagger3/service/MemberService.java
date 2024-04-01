package com.api.swagger3.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.swagger3.model.dto.LoginDTO;
import com.api.swagger3.model.dto.MemberDTO;
import com.api.swagger3.model.dto.MemberSaveDTO;
import com.api.swagger3.model.request.MemberSerchCondition;

public interface MemberService {
    
    public LoginDTO loginMember(String id, String pw) throws Exception;
    public void setMember(MemberSaveDTO memberSaveDTO) throws Exception;
    public Page<MemberDTO> setMembersPage(MemberSerchCondition condition, Pageable pageable) throws Exception;

/**
     * SHA-256으로 해싱하는 메소드
     * 
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException 
     */
    default String sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        
        return bytesToHex(md.digest());
    }

    /**
     * 바이트를 헥스값으로 변환한다.
     * 
     * @param bytes
     * @return
     */
    default String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
          builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
} 
