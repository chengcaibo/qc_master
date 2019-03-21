package com.qichong.token;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.qichong.entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Json Web Token
 * 
 * @author 孙建雷
 *
 */
public class JWToken {

	private static int id = 0;

	private static final String profiles = "q234i62c6h3467o347n34g713733178";

	/** 由字符串生成加密key */
	private static SecretKey generalKey() {
		String stringKey = profiles;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/** 获取公共的Claims */
	private static Claims getClaims(long exp) {
		// 当前时间
		long nowMillis = System.currentTimeMillis();
		// 构造 claims
		Claims claims = new DefaultClaims();
		claims.setId("JWT_" + JWToken.id++);
		claims.setIssuedAt(new Date(nowMillis)); // 发行时间
		claims.setIssuer("QICHONG_SERVICE"); // 发行者

		if (exp >= 0)
			claims.setExpiration(new Date(nowMillis + exp));
		// 构造jwt
		return claims;
	}

	/**
	 * 模板1，构造JWT
	 * 
	 * @param subject
	 *            主题
	 * @param exp
	 *            过期时间
	 */
	public static String buildJWT(String subject, long exp) {
		// 构造 claims
		Claims claims = getClaims(exp);
		claims.setSubject(subject); // 主题
		// 构造jwt
		return buildJWT(claims);
	}

	/**
	 * 模板2，构造JWT
	 * 
	 * @param user
	 *            用户对象
	 */
	public static String buildJWT(Users user) {
		long exp = 1000 * 60 * 60 * 24 * 3; // 过期时间三天
		return buildJWT(user, exp);
	}

	/**
	 * 模板3，构造JWT
	 * 
	 * @param user
	 *            用户对象
	 * @param exp
	 *            过期时间
	 */
	public static String buildJWT(Users user, long exp) {
		Claims claims = getClaims(exp);
		claims.put("u_id", user.getId()); // 用户ID
		claims.put("u_username", user.getUsername());
		claims.put("u_password", user.getPassword());
		claims.put("u_typeId", user.getTypeId());
		return buildJWT(claims);
	}

	/** 构造JWT */
	public static String buildJWT(Claims claims) {
		JwtBuilder builder = Jwts.builder().addClaims(claims);
		builder.setHeaderParam("typ", "jwt");
		builder.signWith(SignatureAlgorithm.HS512, generalKey());
		return builder.compact();
	}

	/** 解析jwt，并只返回body */
	public static Claims parseJWTBody(String jwt) {
		return parseJWT(jwt).getBody();
	}

	/**
	 * 解析jwt
	 * 
	 * @param jwt
	 *            要被解析的JWT字符串
	 * @throws UnsupportedJwtException
	 *             如果提供的JWT不受支持的时候抛出
	 * @throws MalformedJwtException
	 *             如果提供的JWT不是有效的JWT的时候抛出
	 * @throws SignatureException
	 *             如果JWT签名错误时抛出
	 * @throws ExpiredJwtException
	 *             如果JWT提供过期时间，并且已经过期的时候抛出
	 * @throws IllegalArgumentException
	 *             如果JWT为空的时候抛出
	 */
	public static Jws<Claims> parseJWT(String jwt) {
		return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt);
	}

}