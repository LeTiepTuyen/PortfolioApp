package com.example.portfolioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolioapp.ui.theme.PortfolioAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PortfolioAppTheme {
                val status = remember {
                    mutableStateOf(false)
                }
                val showPortfolio = remember {
                    mutableStateOf(false)
                }
                val selectedProject = remember { mutableStateOf<String?>(null) } // Thêm state để lưu trữ dự án được chọn
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(390.dp)
                            .padding(12.dp),
                        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CreateImageProfile()
                            CreatInfo()
                            Button(onClick = {
                                status.value = !status.value
                                showPortfolio.value = !showPortfolio.value // Đảo ngược trạng thái hiển thị danh sách Portfolio
                            }) {
                                Icon(
                                    Icons.Filled.AccountCircle
                                    , contentDescription = "Portfolio Icon",
                                    modifier = Modifier.size(ButtonDefaults.IconSize))
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Portfolio", style = MaterialTheme.typography.labelLarge)
                            }
                            Divider()
                            if (showPortfolio.value) { // Hiển thị danh sách Portfolio nếu biến showPortfolio là true
                                Portfolio(
                                    data = listOf("Project 1", "Project 2", "Project 3"),
                                    onProjectSelected = { projectName ->
                                        selectedProject.value = projectName
                                        status.value = false
                                        showPortfolio.value = false // Ẩn danh sách Portfolio khi chọn một dự án cụ thể
                                    }
                                )
                            } else if (selectedProject.value != null) {
                                ProjectDetailScreen(
                                    projectName = selectedProject.value!!,
                                    onBack = {
                                        selectedProject.value = null
                                        showPortfolio.value = true // Hiển thị lại danh sách Portfolio khi quay lại từ chi tiết dự án
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectDetailScreen(projectName: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = projectName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Description of $projectName",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Skills required for $projectName",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = onBack,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Back")
        }
    }
}

@Preview
@Composable
fun CreatInfo() {
    Column(modifier = Modifier.padding(3.dp)) {
        Text("Le Tiep Tuyen", style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary)
        Text("Automation Testing", style = MaterialTheme.typography.labelLarge)
        Text("@themeCompose", style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun CreateImageProfile() {
    Surface(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp),
        CircleShape,
        border = BorderStroke(0.5.dp, color = Color.LightGray),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        Image(painter = painterResource(id = R.drawable.profile_avatar),
            contentDescription = "profile image",
            modifier = Modifier.size(135.dp),
            contentScale = ContentScale.Crop)

    }
}

@Composable
fun Portfolio(data: List<String>, onProjectSelected: (String) -> Unit) {
    LazyColumn(){
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                shape = RectangleShape
            ) {
                Row (modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clickable { onProjectSelected(item) } // Thêm clickable để bắt sự kiện khi nhấp vào dự án
                ) {
                    CreateImageProfile()
                    Column(modifier = Modifier
                        .padding(7.dp)
                        .align(alignment = Alignment.CenterVertically)
                    ) {
                        Text(text = item, fontWeight = FontWeight.Bold)
                        Text(text = "A great project", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
