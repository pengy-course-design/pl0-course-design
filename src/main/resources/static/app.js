const compileBtn = document.getElementById("compileBtn");
const sourceInput = document.getElementById("sourceInput");

const outputs = {
    tokens: document.getElementById("tokensOutput"),
    ast: document.getElementById("astOutput"),
    symbols: document.getElementById("symbolsOutput"),
    errors: document.getElementById("errorsOutput"),
    quadruples: document.getElementById("quadruplesOutput")
};

compileBtn.addEventListener("click", async () => {
    setLoading(true);

    try {
        const response = await fetch("/api/compile", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ source: sourceInput.value })
        });

        const result = await response.json();
        renderResult(result);
    } catch (error) {
        outputs.errors.textContent = "请求失败：" + error.message;
    } finally {
        setLoading(false);
    }
});

function renderResult(result) {
    outputs.tokens.textContent = formatList(result.tokens);
    outputs.ast.textContent = result.astSummary || "无 AST 信息";
    outputs.symbols.textContent = formatList(result.symbols);

    if (result.syntaxError) {
        outputs.errors.textContent = result.syntaxError;
    } else {
        outputs.errors.textContent = formatList(result.semanticErrors) || "无语义错误";
    }

    outputs.quadruples.textContent = formatList(result.quadruples) || "存在错误时不生成四元式";
}

function formatList(items) {
    if (!items || items.length === 0) {
        return "";
    }

    return items.map((item, index) => `${index + 1}. ${item}`).join("\n");
}

function setLoading(loading) {
    compileBtn.disabled = loading;
    compileBtn.textContent = loading ? "分析中..." : "运行分析";
}