package springstudy.springtest.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import springstudy.springtest.domain.Member;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;//여기서 connection을 하면, 계속 새로운 커넥션이 생긴다.
    }
    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //이건 결과를받는것이다.

        try {
            conn = getConnection();//connection을 가져온다.
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //connection에서 prepareStatement에 sql을 넣는다.
            //두번째 옵션의 RETURN_GENERATED_KEYS는 DB에 인서트를하면, 인서트에 대한 id값을 얻을 수 있는데
            //1,2번 하는 id값을 얻을 수 있는데 그것을 얻을때 쓰는 것이다?
            pstmt.setString(1, member.getName());
            //?로 해서 매칭된것을 member.getName()으로 해서 값을 넣는다.

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);//사용되는 자원들을 바로바로 끊어줘야한다.
            //db connection을 계속 쌓아두면 엄청난 장애가 발생할 수 있다.
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else { return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        } }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
        //DataSourceUtils를 이용해서 connection을 해야한다. 그래야 이전의 트렌젝션이나 이런것이 걸릴때
        //dbconnection을 똑같이 유지해준다.
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
            //close를 하는 과정이 매우 복잡하다.
    {
        try { if (rs != null) {
            rs.close();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
        //닫을때도 datasourceUtils를 이용해서 닫아줘야한다.
    }
}