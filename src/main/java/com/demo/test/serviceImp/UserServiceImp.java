package com.demo.test.serviceImp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.test.entity.UserEntity;
import com.demo.test.repository.UserRepository;
import com.demo.test.request.UserRequest;
import com.demo.test.service.userService;
import com.demo.test.utilities.commonQueryAPIUtils;

@Service
public class UserServiceImp implements userService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public ResponseEntity<?> loginUser(UserRequest reqBody) {
		try {
			String errorMsg = commonQueryAPIUtils.validationService(
					Arrays.asList(reqBody.getEmail(), reqBody.getPassword()), Arrays.asList("email", "password"));
			if (errorMsg.length() > 0) {
				return commonQueryAPIUtils.fStaticResponse(errorMsg);
			} else {
				String email = reqBody.getEmail();
				String password = reqBody.getPassword();

				List<Map<String, Object>> getUserDetails = userRepo.getUserDetails(email, password);
				if (getUserDetails.size() > 0) {

					// return GenerateJwtToken(getUserDetails.get(0));
					return commonQueryAPIUtils.sResponse(" Login successful");


				} else {
					System.err.println("user fail");
					return commonQueryAPIUtils.manualResponse("02", "Invalid credentials!");
				}

			}
		} catch (Exception e) {

			return commonQueryAPIUtils.manualResponse("02", "Invalid credentials!");
		}
	}
	
	
//	private String GenerateJwtToken(Map<String, Object>  user)   {
//        var key = Encoding.UTF8.GetBytes("YourSuperLongSecretKeyWithMoreThan32Characters!");
//        var claims = new List<Claim>
//        {
//            new(ClaimTypes.NameIdentifier, user.Id.ToString()),
//            new(ClaimTypes.Email, user.Email),
//            new(ClaimTypes.Role, user.Role.ToString())
//        };
//
//        var token = new JwtSecurityToken(
//            claims: claims,
//            expires: DateTime.UtcNow.AddHours(3),
//            signingCredentials: new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256)
//        );
//
//        return new JwtSecurityTokenHandler().WriteToken(token);
//    }
//}


	public ResponseEntity<?> createUser(UserRequest user) {
		String message = "";

		try {
			int count = userRepo.alredyRegister(user.getEmail().trim().toLowerCase());
			System.err.println(count);
			if (count > 0) {
				return commonQueryAPIUtils.fDynamicResponse("User already exists with this email");
			} else {

				UserEntity entity = new UserEntity();
				entity.setEmail(user.getEmail().trim().toLowerCase());
				entity.setName(user.getName().trim());
				entity.setPassword(user.getPassword().trim());
				entity.setRole(user.getRole());

				userRepo.save(entity);

				return commonQueryAPIUtils.sResponse(" User created successfully");
			}

		} catch (Exception e) {
			return commonQueryAPIUtils.fCatchResponse(e);
		}
	}

}
