# 饿了么外卖系统分层架构图生成工具

这个项目包含了用于生成饿了么外卖系统分层架构图的Python脚本。

## 安装依赖

在运行脚本之前，您需要安装以下依赖：

1. **Python 3.6+**：确保您已安装Python 3.6或更高版本。
2. **Graphviz**：这是一个图形可视化软件，脚本依赖它来生成图表。
3. **Python Graphviz库**：Python的Graphviz接口。

### 安装Graphviz

#### Windows

1. 下载并安装Graphviz：https://graphviz.org/download/
2. 将Graphviz的bin目录添加到系统PATH环境变量中（通常是`C:\Program Files\Graphviz\bin`）

#### macOS

使用Homebrew安装：

```bash
brew install graphviz
```

#### Linux (Ubuntu/Debian)

```bash
sudo apt-get update
sudo apt-get install graphviz
```

### 安装Python依赖

```bash
pip install graphviz
```

## 运行脚本

我们提供了三个不同版本的脚本，每个脚本生成的图表略有不同：

1. **基础版本**：
```bash
python create_architecture_diagram.py
```

2. **简化版本**：
```bash
python create_elm_architecture_simple.py
```

3. **最终版本**（推荐）：
```bash
python create_elm_architecture_final.py
```

运行脚本后，将在当前目录生成一个PNG格式的架构图。

## 脚本说明

- `create_architecture_diagram.py`：详细的系统分层架构图，包含多个节点和详细的连接关系。
- `create_elm_architecture_simple.py`：简化版的架构图，将相关节点合并，减少图表复杂度。
- `create_elm_architecture_final.py`：最终版本，针对饿了么外卖系统优化，更贴近实际架构。

## 自定义

如果您想自定义架构图，可以编辑相应的Python脚本：

- 修改颜色：调整`colors`字典中的颜色值
- 添加/移除组件：在相应的子图部分添加或删除节点
- 调整连接关系：修改`edge`函数调用
- 更改布局：调整`rankdir`、`nodesep`和`ranksep`参数

## 注意事项

- 确保Graphviz正确安装并添加到系统PATH中
- 如果遇到权限问题，尝试以管理员/sudo权限运行脚本
- 生成的图表可能需要根据实际需求进行调整 