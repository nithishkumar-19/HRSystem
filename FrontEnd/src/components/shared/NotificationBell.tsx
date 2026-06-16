import React from "react";
import { FaBell } from "react-icons/fa";

interface NotificationBellProps {
  count: number;
  callBack?: () => void;
}

const NotificationBell: React.FC<NotificationBellProps> = ({ count, callBack }) => {
  return (
    <div
      onClick={() => callBack?.()}
      style={{
        position: "relative",
        display: "inline-block",
        marginLeft: "15rem",
        cursor: "pointer",
      }}
    >
      <FaBell size={24} />

      {count > 0 && (
        <span
          style={{
            position: "absolute",
            top: "-8px",
            right: "-8px",
            backgroundColor: "red",
            color: "white",
            borderRadius: "50%",
            minWidth: "18px",
            height: "18px",
            fontSize: "12px",
            fontWeight: "bold",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            padding: "2px",
          }}
        >
          {count > 99 ? "99+" : count}
        </span>
      )}
    </div>
  );
};

export default NotificationBell;