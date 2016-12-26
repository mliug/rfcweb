var textMap = new Map();
var url= {
    query_text_by_name: "/rfcweb/rfcweb/file/"
};

function processLine($pre, rfcLine) {
    switch (rfcLine.type) {
        case "TEXT" :
            $pre.append($("<span></span>").text(rfcLine.text)).append($("<br>"));
            break;
        case "TITLE" :
            $pre.append($("<span></span>").text(rfcLine.text)).append($("<br>"));
            break;
        case "EMPTYLINE" :
            for (let i=0; i < rfcLine.level; ++i) {$pre.append($("<br>"));}
            break;
        case "FOOTER" :
            $pre.append($("<span class=\"footer\">></span>").text(rfcLine.text)).append($("<br>"));
            break;
        case "HEADER" :
            $pre.append($("<span class=\"header\">></span>").text(rfcLine.text)).append($("<br>"));
            break;
        case "SEPARATOR" : $pre.append($("<hr>")); break;
    }
}

function buildContentByText(rfcText) {
    var $pre = $("<pre></pre>");
    $pre.attr("id", "textPre");
    $pre.removeClass();
    for (let i = 0; i < rfcText.lines.length; ++i) { processLine($pre, rfcText.lines[i]); }
    textMap.set(rfcText.fileName, $pre);
}

function requestText(name) {
    var ret = 2;
    $.ajax({
        url : url.query_text_by_name + name,
        cache : false,
        async : false,
        type : 'GET',
        dataType : 'json',
        success : function(rsp) {
            if (rsp != null || rsp != undefined) {
                buildContentByText(rsp);
                ret = 0;
            } else {
                ret = 1;
            }
        }
    });
    return ret;
}

function getText(name) {
    var $pre = textMap.get(name);
    if ($pre != undefined) { return $pre; }
    var ret = requestText(name);
    if (ret === 1) { ret = requestText(name);}
    $pre = textMap.get(name);
    return ($pre != undefined) ? $pre : $("<pre id=\"textPre\"></pre>");
}

function RfcWeb() {
    return {
        init : function() {
            $("#load5277").on("click", function(e) {
                e.preventDefault();
                $("#text").empty();
                $("#text").append(getText("rfc5277"));
            });
        }
    };
}

var rfcweb = RfcWeb();
