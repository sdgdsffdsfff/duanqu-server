<%@ page language="java"  isELIgnored="false" pageEncoding="UTF-8"%>

              <div id="pages"> ${vo.totalNumber}条记录 ${vo.page}/${vo.totalPage}页 

              <c:if test="${vo.page!=1}"><a href="#" onclick="query(1)">首页</a><a href="#" onclick="query(prviousPage(${vo.page}))">上一页</a></c:if>
              <c:choose>
              <c:when test="${vo.page<=2&&vo.totalPage>=5}">
              <c:forEach var="x" begin="1" end="5" step="1">
              <c:choose>
              <c:when test="${x==vo.page}">
              <span class="current">${vo.page}</span>
              </c:when>
              <c:otherwise>
              <a href="#" onclick="query(${x})">&nbsp;${x}&nbsp;</a>
              </c:otherwise>
              </c:choose>
              </c:forEach>
              </c:when>
              
              <c:when test="${vo.totalPage<=5}">
              <c:forEach var="x" begin="1" end="${vo.totalPage}" step="1">
              <c:choose>
              <c:when test="${x==vo.page}">
              <span class="current">${vo.page}</span>
              </c:when>
              <c:otherwise>
              <a href="#" onclick="query(${x})">&nbsp;${x}&nbsp;</a>
              </c:otherwise>
              </c:choose>
              </c:forEach>
              </c:when>
              
              <c:when test="${vo.page+2>vo.totalPage}">
              <c:forEach var="x" begin="${vo.totalPage-4}" end="${vo.totalPage}" step="1">
              <c:choose>
              <c:when test="${x==vo.page}">
              <span class="current">${vo.page}</span>
              </c:when>
              <c:otherwise>
              <a href="#" onclick="query(${x})">&nbsp;${x}&nbsp;</a>
              </c:otherwise>
              </c:choose>
              </c:forEach>
              </c:when>

              <c:otherwise>
              <a href="#" onclick="query(${vo.page}-2)">&nbsp;${vo.page-2}&nbsp;</a>
              <a href="#" onclick="query(${vo.page}-1)">&nbsp;${vo.page-1}&nbsp;</a>
              <span class="current">${vo.page}</span>
              <a href="#" onclick="query(${vo.page}+1)">&nbsp;${vo.page+1}&nbsp;</a>
              <a href="#" onclick="query(${vo.page}+2)">&nbsp;${vo.page+2}&nbsp;</a>
              </c:otherwise>
              </c:choose>
			  <input type="text" name="page" value="${vo.page}" size="1"></input>
              <input name="" type="button"  value="跳页" class="button" onClick="query()">
              <c:if test="${vo.page!=vo.totalPage}"><a href="#" onclick="query(nextPage(${vo.page}))">下一页</a><a href="#" onclick="query(${vo.totalPage})">末页</a></c:if> 
            </div>
          