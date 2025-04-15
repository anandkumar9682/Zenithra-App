package com.asuni.zenithra.ui.authentication

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.asuni.zenithra.R
import com.asuni.zenithra.network.model.ApiResponse
import com.asuni.zenithra.ui.theme.HeadingTextSize
import com.asuni.zenithra.ui.theme.MessageTextSize12
import com.asuni.zenithra.ui.theme.MessageTextSize14
import com.asuni.zenithra.ui.theme.PrimaryLight
import com.asuni.zenithra.util.navigateToSignIn
import com.asuni.zenithra.util.showToast
import com.asuni.zenithra.util.validateEmail
import com.asuni.zenithra.util.validateMobile
import com.asuni.zenithra.util.validateName
import com.asuni.zenithra.viewmodels.AuthViewModel
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
fun SignUpScreen(activity: Activity, navController: NavController, viewModel: AuthViewModel) {
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


    val isPasswordVisible = remember { mutableStateOf(false) }

    val name = remember { mutableStateOf("") }
    val nameError = remember { mutableStateOf("") }

    val mobile = remember { mutableStateOf("") }
    val mobileError = remember { mutableStateOf("") }

    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }

    val password = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }

    val signUpStatus by viewModel.signUpStatus.collectAsState()
    LaunchedEffect(signUpStatus) {
        when (signUpStatus) {
            is ApiResponse.Success -> {
                context.showToast( signUpStatus.data?.second ?: "Something went wrong")
                viewModel.clearSignUpRequest()
                navController.navigateToSignIn()
            }
            is ApiResponse.Error -> {
                context.showToast( signUpStatus.errorMessage ?: "Something went wrong")
            }
            else -> {}
        }
    }

    var showToast by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(showToast) {
        if (showToast) {
            keyboardController?.hide()
            delay(5000)
            showToast = false
        }
    }

    var isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black


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
                val (tvAppName, tvWelcome, tvDetails, nameId, mobileId, emailId, passwordId, continueButton, tvSignUpLay) = createRefs()

                val focusManager = LocalFocusManager.current

// Define FocusRequesters for each field
                val focusRequesterName = FocusRequester()
                val focusRequesterMobile = FocusRequester()
                val focusRequesterEmail = FocusRequester()
                val focusRequesterPassword = FocusRequester()

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
                    text = "Sign Up",
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
                    text = "Please enter your details to sign up",
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

                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        nameError.value = if (validateName(it)) "" else if (it.isBlank()) "" else "Enter valid name"
                    },
                    label = { Text("Enter Name", style = TextStyle(fontSize = MessageTextSize14)) },
                    modifier = Modifier
                        .constrainAs(nameId) {
                            top.linkTo(tvDetails.bottom, margin = 15.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterName),
                    isError = nameError.value.isNotEmpty(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterMobile.requestFocus() }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

                OutlinedTextField(
                    value = mobile.value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 10 && newValue.all { it.isDigit() }) {
                            mobile.value = newValue
                            mobileError.value = if (validateMobile(newValue)) "" else if (newValue.isBlank()) "" else "Enter valid 10-digit mobile"
                        }
                    },
                    label = { Text("Enter Mobile", style = TextStyle(fontSize = MessageTextSize14)) },
                    modifier = Modifier
                        .constrainAs(mobileId) {
                            top.linkTo(nameId.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterMobile),
                    isError = mobileError.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterEmail.requestFocus() }
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

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
                            top.linkTo(mobileId.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterEmail),
                    isError = emailError.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterPassword.requestFocus() }
                    ),
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
                            top.linkTo(emailId.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp)
                        .focusRequester(focusRequesterPassword),
                    isError = passwordError.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

                Button(
                    onClick = {
                        nameError.value = if (validateName(name.value)) "" else "Enter valid name"
                        mobileError.value = if (validateMobile(mobile.value)) "" else "Enter valid 10-digit mobile"
                        emailError.value = if (validateEmail(email.value)) "" else "Enter valid email"
                        passwordError.value = if (password.value.isNotBlank()) "" else "Please enter password"

                        val isFormValid = nameError.value.isEmpty() &&
                                mobileError.value.isEmpty() &&
                                emailError.value.isEmpty() &&
                                passwordError.value.isEmpty()

                        if (signUpStatus !is ApiResponse.Loading && isFormValid) {
                            viewModel.signUp(
                                name = name.value,
                                mobile = mobile.value,
                                email = email.value,
                                password = password.value
                            )
                        }
                    },
                    modifier = Modifier
                        .testTag("sign_up_button")
                        .fillMaxWidth()
                        .height(50.dp)
                        .constrainAs(continueButton) {
                            top.linkTo(passwordId.bottom, margin = 30.dp)
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
                            visible = signUpStatus is ApiResponse.Loading,
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
                            text = if (signUpStatus is ApiResponse.Loading) "Please wait..." else "Sign Up",
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
                    Text("Have an account?", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sign In",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigateToSignIn()
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
                    text = signUpStatus.data?.second ?: "Something went wrong.",
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