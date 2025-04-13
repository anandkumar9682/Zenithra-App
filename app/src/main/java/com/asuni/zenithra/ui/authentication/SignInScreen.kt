package com.asuni.zenithra.ui.authentication

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.asuni.zenithra.R
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.ui.navigations.NavRoute
import com.asuni.zenithra.ui.theme.HeadingTextSize
import com.asuni.zenithra.ui.theme.MessageTextSize12
import com.asuni.zenithra.ui.theme.MessageTextSize14
import com.asuni.zenithra.ui.theme.PrimaryLight
import com.asuni.zenithra.util.navigateToDashboard
import com.asuni.zenithra.util.navigateToSignIn
import com.asuni.zenithra.util.navigateToSignUp
import com.asuni.zenithra.util.showToast
import com.asuni.zenithra.util.validateEmail
import com.asuni.zenithra.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.delay

/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-14
 * Description: Login screen for user authentication, supporting email/password login and Google sign-in.
 * Handles back press actions, password visibility, and form validation. Displays feedback based on login status.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(activity: Activity, navController: NavController, viewModel: AuthViewModel) {
    val context =  LocalContext.current
    var lastBackPressedTime by remember { mutableStateOf(0L) } // Track last back press time
    val backPressInterval = 2000 // 2 seconds

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressedTime < backPressInterval) {
            activity.finish() // Close the app
        } else {
            lastBackPressedTime = currentTime
            context.showToast("Press again to exit")
        }
    }

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }


    val focusRequesterEmailId = remember { FocusRequester() }
    val isFocusedEmailId = remember { mutableStateOf(false) }

    val focusRequesterPassword = remember { FocusRequester() }
    val isFocusedPassword = remember { mutableStateOf(false) }

    val firebaseAuthWithGoogleStatus by viewModel.firebaseAuthWithGoogleStatus.collectAsState()
    LaunchedEffect(firebaseAuthWithGoogleStatus) {
        when (firebaseAuthWithGoogleStatus) {
            is ApiResponse.Success -> {
                context.showToast( firebaseAuthWithGoogleStatus.data?.second ?: "Something went wrong")
                viewModel.clearLoginRequest()
                navController.navigateToDashboard()
            }
            is ApiResponse.Error -> {
                context.showToast( firebaseAuthWithGoogleStatus.errorMessage ?: "Something went wrong")
            }
            else -> {}
        }
    }

    val loginStatus by viewModel.loginStatus.collectAsState()
    LaunchedEffect(loginStatus) {
        when (loginStatus) {
            is ApiResponse.Success -> {
                context.showToast( loginStatus.data?.second ?: "Something went wrong")
                viewModel.clearLoginRequest()
                navController.navigateToDashboard()
            }
            is ApiResponse.Error -> {
                context.showToast( loginStatus.errorMessage ?: "Something went wrong")
            }
            else -> {}
        }
    }

    val resetPasswordStatus by viewModel.resetPasswordStatus.collectAsState()
    var errorMessage = remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }

    var isLoading = remember { mutableStateOf(false) }
    LaunchedEffect(resetPasswordStatus) {
        when (resetPasswordStatus) {
            is ApiResponse.Loading -> {
                isLoading.value = true
            }
            is ApiResponse.Success -> {
                showToast = true
                isLoading.value = false
            }
            is ApiResponse.Error -> {
                errorMessage.value = resetPasswordStatus.errorMessage ?: "Something went wrong"
                isLoading.value = false
            }
            else -> {
                isLoading.value = false
            }
        }
    }

    var showForgetPassword  by remember { mutableStateOf(false)}
    if (showForgetPassword) {
        ResentPasswordDialog (
            isLoading = isLoading,
            errorMessage = errorMessage,
            viewModel = viewModel,
            onDismissRequest = { showForgetPassword = false },
        )
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(showToast) {
        if (showToast) {
            keyboardController?.hide()
            showForgetPassword = false
            delay(5000)
            showToast = false
        }
    }

    var isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        try {
            if (data == null) {
                context.showToast("Google Sign-In Failed: No data")
                return@rememberLauncherForActivityResult
            }

            val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                .getResult(ApiException::class.java)

            val idToken = account?.idToken
            if (idToken.isNullOrEmpty()) {
                context.showToast("Google Sign-In Failed: Token null")
            } else {
                viewModel.firebaseAuthWithGoogle(idToken)
            }
        } catch (e: Exception) {
            Log.d("TestLog",e.toString())
            context.showToast("Google Sign-In Failed: ${e.message}")
        }
    }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(stringResource(R.string.default_web_client_id)) // Replace with real client ID
        .requestEmail()
        .build()

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(10.dp),
    )
    {
        val (detailsLay, layResetPass) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(detailsLay){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .wrapContentSize()
                .background(
                    color = surfaceColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 25.dp, horizontal = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout() {
                val (
                    tvAppName, tvWelcome, tvDetails,btnGoogle, btnApple,
                    emailId, passwordId, forgetPassword,
                    continueButton,
                    dividerRow, signupType, tvSignUpLay, toastMessage
                ) = createRefs()

                Text(
                    text = "Zenithra",
                    fontSize = MessageTextSize14,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .testTag("app_logo")
                        .constrainAs(tvAppName) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    text = "Welcome Back",
                    fontSize = HeadingTextSize,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .testTag("welcome_header")
                        .padding(top = 7.dp)
                        .constrainAs(tvWelcome) {
                            top.linkTo(tvAppName.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Text(
                    text = "Please enter your details to sign in",
                    fontSize = MessageTextSize12,
                    color = Color.Gray,
                    modifier = Modifier
                        .testTag("signin_subtitle")
                        .padding(top = 7.dp)
                        .constrainAs(tvDetails) {
                            top.linkTo(tvWelcome.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp) // space from top element
                        .constrainAs(signupType) {
                            top.linkTo(tvDetails.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    // Google Button
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .border(1.dp, Color.Gray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                val signInIntent = googleSignInClient.signInIntent
                                launcher.launch(signInIntent)
                            },
                            modifier = Modifier.size(38.dp)
                        ) {
                            if (firebaseAuthWithGoogleStatus is ApiResponse.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(5.dp),
                                    color = backgroundColor,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_google),
                                    contentDescription = "Sign in with Google",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    // Apple Button
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .border(1.dp, Color.Gray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { /* Apple Sign-In */ },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_apple),
                                contentDescription = "Sign in with Apple",
                                tint = textColor
                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(dividerRow) {
                            top.linkTo(signupType.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp, modifier = Modifier.weight(1f))
                    Text("OR", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))
                    Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp, modifier = Modifier.weight(1f))
                }

                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailError.value = if (validateEmail(it)) "" else if (it.isNullOrBlank()) "" else "Please enter a valid email address"
                    },
                    label = {
                        Text(
                            text = "Enter Email Address",
                            style = TextStyle(fontSize = MessageTextSize14)
                        )
                    },
                    modifier = Modifier
                        .testTag("email_input")
                        .constrainAs(emailId) {
                            top.linkTo(dividerRow.bottom, margin = 4.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterEmailId)
                        .onFocusChanged {
                            isFocusedEmailId.value = it.isFocused
                        },
                    isError = emailError.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

                OutlinedTextField(
                    value = password.value,
                    onValueChange = {
                        if (!it.isNullOrBlank()) {
                            password.value = it
                            passwordError.value = ""
                        } else {
                            password.value = ""
                            passwordError.value = "Please enter your password"
                        }
                    },
                    label = {
                        Text(
                            text = "Enter password",
                            style = TextStyle(fontSize = MessageTextSize14)
                        )
                    },
                    modifier = Modifier
                        .testTag("password_input")
                        .constrainAs(passwordId) {
                            top.linkTo(emailId.bottom, margin = 15.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterPassword)
                        .onFocusChanged {
                            isFocusedPassword.value = it.isFocused
                        },
                    isError = passwordError.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (isPasswordVisible.value) KeyboardType.Text else KeyboardType.Password
                    ),
                    visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPasswordVisible.value) R.drawable.eye_open else R.drawable.eye_close
                                ),
                                contentDescription = if (isPasswordVisible.value) "Hide Password" else "Show Password",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

                Text(
                    text = "Forgot password?",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MessageTextSize14,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier
                        .testTag("forgot_password")
                        .constrainAs(forgetPassword) {
                            top.linkTo(passwordId.bottom, margin = 10.dp)
                            end.linkTo(parent.end)
                        }
                        .clickable { showForgetPassword = true }
                )

                Button(
                    onClick = {
                        if (loginStatus !is ApiResponse.Loading && validateEmail(email.value) && password.value.isNotBlank()) {
                            emailError.value = ""
                            viewModel.mobileNumber = email.value
                            viewModel.countryCode = password.value
                            viewModel.login(email = email.value, password = password.value)
                        }
                    },
                    modifier = Modifier
                        .testTag("sign_in_button")
                        .fillMaxWidth()
                        .height(50.dp)
                        .constrainAs(continueButton) {
                            top.linkTo(forgetPassword.bottom, margin = 30.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (validateEmail(email.value) && password.value.isNotBlank()) backgroundColor else backgroundColor.copy(alpha = 0.2f),
                        contentColor = when {
                            validateEmail(email.value) && password.value.isNotBlank() -> White
                            isDarkTheme -> White.copy(alpha = 0.2f)
                            else -> PrimaryLight.copy(alpha = 0.5f)
                        }
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AnimatedVisibility(
                            visible = loginStatus is ApiResponse.Loading,
                            enter = fadeIn(tween(500, easing = LinearEasing)),
                            exit = fadeOut(tween(300))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(30.dp)
                                    .padding(end = 8.dp),
                                color = White,
                                strokeWidth = 2.dp
                            )
                        }
                        Text(
                            text = if (loginStatus is ApiResponse.Loading) "Please wait..." else "Sign In",
                            style = TextStyle(fontWeight = FontWeight.Normal)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .constrainAs(tvSignUpLay) {
                            top.linkTo(continueButton.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Don't have an account?", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigateToSignUp()
                        }
                    )
                }
            }
        }

        if (showToast) {
            Row(
                modifier = Modifier
                    .testTag("success_toast")
                    .fillMaxWidth()
                    .constrainAs(layResetPass) {
                        bottom.linkTo(parent.bottom, margin = 20.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(surfaceColor, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .zIndex(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_success),
                    contentDescription = "Status Icon",
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = resetPasswordStatus.data?.second ?: "Something went wrong.",
                    color = textColor,
                    fontSize = MessageTextSize14,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.icon_close),
                    contentDescription = "Close",
                    tint = textColor,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { showToast = false }
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

