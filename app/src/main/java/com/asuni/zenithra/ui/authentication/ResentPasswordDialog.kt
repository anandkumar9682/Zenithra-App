/**
 * Author: Anand Kumar
 * Email: aanandkumarbind9682@gmail.com
 * Date: 2025-04-13
 * Description: Composable function that displays a dialog for password reset. The dialog includes an email input field, validation, and a submit button for sending the password reset request.
 */

package com.asuni.zenithra.ui.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.asuni.zenithra.R
import com.asuni.zenithra.ui.theme.GrayText
import com.asuni.zenithra.ui.theme.HeadingTextSize
import com.asuni.zenithra.ui.theme.MessageTextSize14
import com.asuni.zenithra.ui.theme.MessageTextSize16
import com.asuni.zenithra.ui.theme.PrimaryLight
import com.asuni.zenithra.util.validateEmail
import com.asuni.zenithra.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResentPasswordDialog(
    isLoading: MutableState<Boolean>,
    errorMessage: MutableState<String>,
    viewModel: AuthViewModel,
    onDismissRequest: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val validationErrorEmailId = remember { errorMessage }

    // Theme colors based on dark/light mode
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else MaterialTheme.colorScheme.primary
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    // Dialog component
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = false) // Prevent closing when clicking outside
    ) {
        Surface(
            modifier =
            Modifier.wrapContentSize(),
            shape = RoundedCornerShape(10.dp),
            color = backgroundColor
        ) {
            ConstraintLayout(
                modifier = Modifier.background(color = surfaceColor).padding(15.dp)
            ) {
                val (title, btnClose, divider, titleMessage, etEmailId, tvErrorEmailId, btnSubmit) = createRefs()

                // Title of the dialog
                Text(
                    text = "Forgot Password",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = HeadingTextSize,
                        color = textColor
                    ),
                    modifier = Modifier
                        .constrainAs(title) {
                            top.linkTo(btnClose.top)
                            start.linkTo(parent.start)
                            bottom.linkTo(btnClose.bottom)
                        }
                )

                // Close button (icon button)
                Box(
                    modifier = Modifier
                        .background(
                            color = surfaceColor,
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = GrayText,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable { }
                        .size(30.dp)
                        .constrainAs(btnClose) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                ) {
                    IconButton(
                        onClick = { onDismissRequest.invoke() },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_close),
                            contentDescription = "Close Icon",
                            tint = textColor
                        )
                    }
                }

                // Divider
                Divider(
                    color = GrayText,
                    thickness = 1.dp,
                    modifier = Modifier.constrainAs(divider) {
                        top.linkTo(btnClose.bottom, margin = 10.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                // Instructions text
                Text(
                    text = "Reset your password by entering your email id",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = MessageTextSize16,
                        color = textColor
                    ),
                    color = textColor,
                    modifier = Modifier
                        .constrainAs(titleMessage) {
                            top.linkTo(divider.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                        }
                )

                // Email input field
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        validationErrorEmailId.value = if (validateEmail(it)) "" else if (it.isNullOrBlank()) "" else "Please enter a valid email address"
                    },
                    label = {
                        Text(
                            text = "Enter Email Address",
                            style = TextStyle(fontSize = MessageTextSize14)
                        )
                    },
                    modifier = Modifier
                        .testTag("email_input")
                        .constrainAs(etEmailId) {
                            top.linkTo(titleMessage.bottom, margin = 10.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .height(62.dp),
                    isError = validationErrorEmailId.value.isNotEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    )
                )

                // Error message for email validation
                if (validationErrorEmailId.value.isNotEmpty()) {
                    Text(
                        text = validationErrorEmailId.value,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.constrainAs(tvErrorEmailId) {
                            top.linkTo(etEmailId.bottom, margin = 4.dp)
                            start.linkTo(parent.start)
                        }
                    )
                }

                // Submit button
                Button(
                    onClick = {
                        if (!isLoading.value)
                            viewModel.resetPassword(email.value)
                    },
                    modifier = Modifier
                        .height(45.dp)
                        .constrainAs(btnSubmit) {
                            top.linkTo(etEmailId.bottom, margin = 40.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 15.dp)
                            width = Dimension.fillToConstraints
                        },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (validateEmail(email.value) && email.value.isNotBlank()) backgroundColor else backgroundColor.copy(alpha = 0.2f),
                        contentColor = when {
                            validateEmail(email.value) && email.value.isNotBlank() -> White
                            isDarkTheme -> White.copy(alpha = 0.2f)
                            else -> PrimaryLight.copy(alpha = 0.5f)
                        }
                    ),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AnimatedVisibility(
                            visible = isLoading.value,
                            enter = fadeIn(
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = LinearEasing
                                )
                            ),
                            exit = fadeOut(animationSpec = tween(durationMillis = 300))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .size(30.dp)
                                    .padding(top = 5.dp, end = 8.dp),
                                color = White,
                                strokeWidth = 2.dp
                            )
                        }
                        Text(
                            text = if (isLoading.value) "Request Sending" else "Submit",
                            style = TextStyle(fontWeight = FontWeight.Normal)
                        )
                    }
                }
            }
        }
    }
}
